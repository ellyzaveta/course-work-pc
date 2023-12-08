package com.kpi.server.threads;

import com.kpi.api.sockets.ServerSocketWrapper;
import com.kpi.api.sockets.SocketWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Primary
@RequiredArgsConstructor
public class MainThread {

    @Value("${server.port}")
    private int serverPort;

    private final ProcessingThread indexProcessingThread;

    public void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try (ServerSocketWrapper serverSocketWrapper = new ServerSocketWrapper(serverPort)) {
            while (true) {
                Optional<SocketWrapper> optionalSocketWrapper = serverSocketWrapper.connect();
                optionalSocketWrapper.ifPresent(clientSocketWrapper -> executorService.execute(new ClientHandlingThread(indexProcessingThread, clientSocketWrapper)));
            }
        } catch (Exception e) {
            System.out.println("Error connecting to client socket");
        }
    }
}
