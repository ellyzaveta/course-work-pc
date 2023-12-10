package com.kpi.client.utils;

import com.kpi.api.dto.requests.Request;
import com.kpi.api.dto.responses.CurrentProgressResponse;
import com.kpi.api.dto.responses.DocumentsSetResponse;
import com.kpi.api.dto.responses.Response;
import com.kpi.api.dto.responses.ThreadsNumberResponse;
import com.kpi.client.ui.Console;

import java.util.Optional;

public class ServerResponseHandler {

    public static Optional<Request> handleServerResponse(Response response) {
        if(response instanceof DocumentsSetResponse) {
            return Optional.ofNullable(handleDocumentsSetResponse((DocumentsSetResponse) response));
        } else if (response instanceof CurrentProgressResponse) {
            return Optional.of(handleCurrentProgressResponse((CurrentProgressResponse) response));
        } else if (response instanceof ThreadsNumberResponse) {
            return Optional.of(handleThreadsNumberResponse());
        } else {
            return Optional.empty();
        }
    }

    private static Request handleDocumentsSetResponse(DocumentsSetResponse documentsSetResponse) {
        Console.showProgressBar(100);
        Console.printResult(documentsSetResponse.getKeyword(), documentsSetResponse.getDocs());

        return null;
    }

    private static Request handleCurrentProgressResponse(CurrentProgressResponse currentProgressResponse) {
        double progress = currentProgressResponse.getProgress();
        if(progress != 100) Console.showProgressBar(progress);

        return RequestFactory.getCurrentProgressRequest();
    }

    private static Request handleThreadsNumberResponse() {
        int numOfThreads = Console.printThreadsNumberRequest();

        Request request = RequestFactory.getThreadsNumberRequest(numOfThreads);
        Console.printSpace();

        return request;
    }
}
