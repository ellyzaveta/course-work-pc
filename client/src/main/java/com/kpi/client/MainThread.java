package com.kpi.client;

import com.kpi.api.dto.requests.DocumentsSetRequest;
import com.kpi.api.dto.requests.ExitRequest;
import com.kpi.api.sockets.SocketWrapper;
import com.kpi.client.services.ServerResponseHandlingService;
import com.kpi.client.utils.Console;

public class MainThread {

    String serverHost = "localhost";
    int serverPort = 1234;
    SocketWrapper socketWrapper;

    public void run() {
        Console.printGreeting();
        socketWrapper = new SocketWrapper(serverHost, serverPort);

        while(true) {
            String keyword = Console.printOptions();
            if (!keyword.equals("e")) {
                handleKeyword(keyword);
            } else {
                handleExit();
            }
        }

    }

    private void handleKeyword(String keyword) {

        DocumentsSetRequest setOfDocsRequest = new DocumentsSetRequest();
        setOfDocsRequest.setKeyword(keyword);

        socketWrapper.send(setOfDocsRequest);

        ServerResponseHandlingService.handleServerResponse(socketWrapper);
    }

    private void handleExit() {
        socketWrapper.send(new ExitRequest());
        System.exit(0);
    }
}
