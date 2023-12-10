package com.kpi.client;

import com.kpi.api.sockets.SocketWrapper;
import com.kpi.client.services.ServerResponsesHandlingService;
import com.kpi.client.ui.Console;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@RequiredArgsConstructor
public class MainThread {

    @Value("${server.host}")
    private String serverHost;

    @Value("${server.port}")
    private int serverPort;

    public void run() {

        SocketWrapper socketWrapper = new SocketWrapper(serverHost, serverPort);

        Console.printGreeting();
        Console.printWaitMessage();

        ServerResponsesHandlingService.handleServerResponses(socketWrapper);
    }

}
