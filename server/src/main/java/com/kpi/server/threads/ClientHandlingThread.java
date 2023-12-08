package com.kpi.server.threads;

import com.kpi.api.dto.requests.*;
import com.kpi.api.dto.responses.*;
import com.kpi.api.sockets.SocketWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ClientHandlingThread implements Runnable {

    SocketWrapper socketWrapper;
    String keyword;
    ProcessingThread processingThread;

    public ClientHandlingThread(ProcessingThread indexProcessingThread, SocketWrapper clientSocketWrapper) {
        this.processingThread = indexProcessingThread;
        this.socketWrapper = clientSocketWrapper;
    }

    @Override
    public void run() {
        while(true) {
            Request request = socketWrapper.read(Request.class);

            Optional<Response> optionalResponse = handleRequest(request);

            if(optionalResponse.isPresent()) {
                Response response = optionalResponse.get();
                if (response instanceof ExitResponse) {
                    break;
                } else {
                    socketWrapper.send(optionalResponse.get());
                }
            }
        }
        try {
            socketWrapper.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Optional<Response> handleRequest(Request request) {

        if (request instanceof DocumentsSetRequest) {
            log.info("Request from client on port " + socketWrapper.getPort() + " " + request);
            return Optional.of(handleSetOfDocsRequest((DocumentsSetRequest) request));
        } else if (request instanceof ThreadsNumberRequest) {
            log.info("Request from client on port " + socketWrapper.getPort() + " " + request);
            return Optional.of(handleNumOfThreadsRequest((ThreadsNumberRequest) request));
        } else if (request instanceof CurrentProgressRequest) {
            return Optional.ofNullable(handleCurrentProgressRequest((CurrentProgressRequest) request));
        } else if (request instanceof ExitRequest) {
            log.info("Request from client on port " + socketWrapper.getPort() + " " + request);
            return Optional.of(new ExitResponse());
        }

        return Optional.empty();
    }

    private Response handleCurrentProgressRequest(CurrentProgressRequest currentProgressRequest) {
        if (processingThread.isInProgress()) {
            CurrentProgressResponse currentProgressResponse = new CurrentProgressResponse();
            currentProgressResponse.setProgress(processingThread.getProgress());
            return currentProgressResponse;
        } else if (processingThread.isIndexed()) {

            DocumentsSetResponse setOfDocsResponse = new DocumentsSetResponse();
            setOfDocsResponse.setDocs(processingThread.get(keyword));
            setOfDocsResponse.setKeyword(keyword);

            keyword = null;

            return setOfDocsResponse;
        }

        return null;
    }

    private Response handleSetOfDocsRequest(DocumentsSetRequest documentsSetRequest) {
        keyword = documentsSetRequest.getKeyword();

        if (processingThread.isIndexed()) {
            return handleSetOfDocsResponse();
        }

        if (processingThread.isInProgress()) {
            return handleCurrentProgressResponse(documentsSetRequest);
        }

        return handleNumOfThreadsResponse(documentsSetRequest);
    }

    private DocumentsSetResponse handleSetOfDocsResponse() {

        DocumentsSetResponse setOfDocsResponse = new DocumentsSetResponse();
        setOfDocsResponse.setDocs(getResult(keyword));

        keyword = null;

        return setOfDocsResponse;
    }

    private List<String> getResult(String keyword) {
        return processingThread.get(keyword);
    }

    private CurrentProgressResponse handleCurrentProgressResponse(DocumentsSetRequest setOfDocsRequest) {

        CurrentProgressResponse currentProgressResponse = new CurrentProgressResponse();
        double progress = processingThread.getProgress();
        currentProgressResponse.setProgress(progress);

        return currentProgressResponse;
    }

    private ThreadsNumberResponse handleNumOfThreadsResponse(DocumentsSetRequest setOfDocsRequest) {
        return new ThreadsNumberResponse();
    }

    private Response handleNumOfThreadsRequest(ThreadsNumberRequest numOfThreadsRequest) {

        int threadsNum = numOfThreadsRequest.getThreadsNumber();

        if (processingThread.isInProgress()) {
            CurrentProgressResponse currentProgressResponse = new CurrentProgressResponse();
            currentProgressResponse.setProgress(processingThread.getProgress());

            return currentProgressResponse;
        }

        processingThread.index(threadsNum);

        CurrentProgressResponse currentProgressResponse = new CurrentProgressResponse();
        currentProgressResponse.setProgress(processingThread.getProgress());

        return currentProgressResponse;
    }
}
