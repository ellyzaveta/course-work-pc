package com.kpi.client;

import com.kpi.api.sockets.SocketWrapper;
import com.kpi.client.services.ServerResponseHandlingService;
import com.kpi.client.utils.Console;

public class MainThread {

    String serverHost = "localhost";
    int serverPort = 1234;
    SocketWrapper socketWrapper;

    public void run() {
        socketWrapper = new SocketWrapper(serverHost, serverPort);

        Console.printGreeting();
        Console.printWaitMessage();

        ServerResponseHandlingService.start(socketWrapper);
    }

}
