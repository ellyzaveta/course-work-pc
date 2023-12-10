package com.kpi.server.utils;

import com.kpi.api.dto.requests.*;
import com.kpi.api.dto.responses.*;
import com.kpi.server.threads.ProcessingThread;

import java.util.List;
import java.util.Optional;

public class RequestHandler {

    private final ProcessingThread processingThread;
    private String keyword;

    public RequestHandler(ProcessingThread processingThread) {
        this.processingThread = processingThread;
    }

    public Optional<Response> handleRequest(Request request) {

        if (request instanceof DocumentsSetRequest) {
            return Optional.of(handleDocumentsSetRequest((DocumentsSetRequest) request));
        } else if (request instanceof ThreadsNumberRequest) {
            return Optional.of(handleThreadsNumberRequest((ThreadsNumberRequest) request));
        } else if (request instanceof CurrentProgressRequest) {
            return Optional.ofNullable(handleCurrentProgressRequest());
        } else if (request instanceof ExitRequest) {
            return Optional.of(new ExitResponse());
        }

        return Optional.empty();
    }

    private Response handleCurrentProgressRequest() {
        if (processingThread.isInProgress()) {
            return ResponseFactory.getCurrentProgressResponse(processingThread.getProgress());
        } else if (processingThread.isIndexed()) {
            return ResponseFactory.getDocumentsSetResponse(getResult(keyword), keyword);
        }

        return null;
    }

    private Response handleDocumentsSetRequest(DocumentsSetRequest documentsSetRequest) {
        keyword = documentsSetRequest.getKeyword();

        if (processingThread.isIndexed()) {
            return ResponseFactory.getDocumentsSetResponse(getResult(keyword), keyword);
        }

        if (processingThread.isInProgress()) {
            return ResponseFactory.getCurrentProgressResponse(getProgress());
        }

        return ResponseFactory.getThreadsNumberResponse();
    }

    private Response handleThreadsNumberRequest(ThreadsNumberRequest threadsNumberRequest) {

        int threadsNumber = threadsNumberRequest.getThreadsNumber();

        if (processingThread.isInProgress()) {
            return ResponseFactory.getCurrentProgressResponse(getProgress());
        }

        processingThread.index(threadsNumber);
        return ResponseFactory.getCurrentProgressResponse(getProgress());
    }

    private List<String> getResult(String keyword) {
        return processingThread.get(keyword);
    }

    private double getProgress() {
        return processingThread.getProgress();
    }
}
