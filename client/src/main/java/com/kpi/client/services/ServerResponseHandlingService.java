package com.kpi.client.services;

import com.kpi.api.dto.requests.CurrentProgressRequest;
import com.kpi.api.dto.requests.Request;
import com.kpi.api.dto.requests.ThreadsNumberRequest;
import com.kpi.api.dto.responses.CurrentProgressResponse;
import com.kpi.api.dto.responses.DocumentsSetResponse;
import com.kpi.api.dto.responses.Response;
import com.kpi.api.dto.responses.ThreadsNumberResponse;
import com.kpi.api.sockets.SocketWrapper;
import com.kpi.client.utils.Console;

import java.util.List;
import java.util.Optional;

public class ServerResponseHandlingService {

    public static void handleServerResponse(SocketWrapper socketWrapper) {

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

        return request;
    }
}
