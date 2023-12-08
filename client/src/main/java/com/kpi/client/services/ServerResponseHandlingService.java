package com.kpi.client.services;

import com.kpi.api.dto.requests.*;
import com.kpi.api.dto.responses.*;
import com.kpi.api.sockets.SocketWrapper;
import com.kpi.client.utils.Console;

import java.util.List;
import java.util.Optional;

public class ServerResponseHandlingService {

    public static void start(SocketWrapper socketWrapper) {
        if(isServerReady(socketWrapper)) {
            while (true) {
                String keyword = Console.printOptions();
                if (!keyword.equals("e")) {
                    handleKeyword(keyword, socketWrapper);
                } else {
                    handleExit(socketWrapper);
                }
            }
        }
    }

    private static boolean isServerReady(SocketWrapper socketWrapper) {
        Response response = socketWrapper.read(Response.class);
        return response instanceof ServerReadyResponse;
    }

    private static void handleKeyword(String keyword, SocketWrapper socketWrapper) {

        DocumentsSetRequest setOfDocsRequest = new DocumentsSetRequest();
        setOfDocsRequest.setKeyword(keyword);
        socketWrapper.send(setOfDocsRequest);

        handleServerResponses(socketWrapper);
    }

    private static void handleExit(SocketWrapper socketWrapper) {
        socketWrapper.send(new ExitRequest());
        System.exit(0);
    }

    private static void handleServerResponses(SocketWrapper socketWrapper) {

        while(true) {
            Response response = socketWrapper.read(Response.class);
            Optional<Request> request = handleServerResponse(response);
            if(request.isPresent()) {
                socketWrapper.send(request.get());
            } else {
                break;
            }
        }
    }

    private static Optional<Request> handleServerResponse(Response response) {
        if(response instanceof DocumentsSetResponse) {
            return Optional.ofNullable(handleSetOfDocsResponse((DocumentsSetResponse) response));
        } else if (response instanceof CurrentProgressResponse) {
            return Optional.of(handleCurrentProgressResponse((CurrentProgressResponse) response));
        } else if (response instanceof ThreadsNumberResponse) {
            return Optional.of(handleNumOfThreadsResponse((ThreadsNumberResponse) response));
        } else {
            return Optional.empty();
        }
    }

    private static Request handleSetOfDocsResponse(DocumentsSetResponse setOfDocsResponse) {
        Console.showFinalProgressBar();
        Console.printResult(setOfDocsResponse.getKeyword(), setOfDocsResponse.getDocs());
        List<String> result = setOfDocsResponse.getDocs();
        return null;
    }

    private static Request handleCurrentProgressResponse(CurrentProgressResponse currentProgressResponse) {
        double progress = currentProgressResponse.getProgress();

        Console.showProgressBar(progress);

        return new CurrentProgressRequest();
    }

    private static Request handleNumOfThreadsResponse(ThreadsNumberResponse numOfThreadsResponse) {
        int numOfThreads = Console.printThreadsNumberRequest();

        ThreadsNumberRequest request = new ThreadsNumberRequest();
        request.setThreadsNumber(numOfThreads);

        Console.printSpace();

        return request;
    }
}
