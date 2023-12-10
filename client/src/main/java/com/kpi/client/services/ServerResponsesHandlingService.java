package com.kpi.client.services;

import com.kpi.api.dto.requests.*;
import com.kpi.api.dto.responses.*;
import com.kpi.api.sockets.SocketWrapper;
import com.kpi.client.ui.Console;
import com.kpi.client.utils.RequestFactory;
import com.kpi.client.utils.ServerResponseHandler;

import java.util.Optional;

public class ServerResponsesHandlingService {

    public static void handleServerResponses(SocketWrapper socketWrapper) {
        if (!isServerReady(socketWrapper)) {
            return;
        }

        processInputUntilExit(socketWrapper);
        handleExit(socketWrapper);
    }

    private static boolean isServerReady(SocketWrapper socketWrapper) {
        Response response = socketWrapper.read(Response.class);
        return response instanceof ServerReadyResponse;
    }

    private static void processInputUntilExit(SocketWrapper socketWrapper) {
        String keyword = Console.readKeyword();

        while (!keyword.equals("e")) {
            handleKeyword(keyword, socketWrapper);
            keyword = Console.readKeyword();
        }
    }

    private static void handleKeyword(String keyword, SocketWrapper socketWrapper) {
        Request documentsSetRequest = RequestFactory.getDocumentsSetRequest(keyword);
        socketWrapper.send(documentsSetRequest);

        processServerResponses(socketWrapper);
    }

    private static void handleExit(SocketWrapper socketWrapper) {
        socketWrapper.send(new ExitRequest());
        System.exit(0);
    }

    private static void processServerResponses(SocketWrapper socketWrapper) {

        while(true) {
            Response response = socketWrapper.read(Response.class);
            Optional<Request> request = ServerResponseHandler.handleServerResponse(response);
            if (request.isPresent()) {
                socketWrapper.send(request.get());
            } else {
                break;
            }
        }
    }

}
