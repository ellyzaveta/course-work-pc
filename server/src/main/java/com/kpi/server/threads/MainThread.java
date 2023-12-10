package com.kpi.server.threads;

import com.kpi.api.sockets.ServerSocketWrapper;
import com.kpi.api.sockets.SocketWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Primary
@RequiredArgsConstructor
@Slf4j
public class MainThread {

    @Value("${server.port}")
    private int serverPort;

    private final ProcessingThread indexProcessingThread;

    public void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        try (ServerSocketWrapper serverSocketWrapper = new ServerSocketWrapper(serverPort)) {
            while (true) {
                Optional<SocketWrapper> optionalSocketWrapper = serverSocketWrapper.connect();
                optionalSocketWrapper.ifPresent(clientSocketWrapper -> executorService.execute(new ClientHandlingThread(indexProcessingThread, clientSocketWrapper)));
            }
        } catch (Exception e) {
            log.info("Error connecting to client socket.");
        }
    }

}
