package com.kpi.server.threads;

import com.kpi.api.dto.requests.*;
import com.kpi.api.dto.responses.*;
import com.kpi.api.sockets.SocketWrapper;
import com.kpi.server.utils.RequestHandler;
import com.kpi.server.utils.ResponseFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class ClientHandlingThread implements Runnable {

    private final SocketWrapper socketWrapper;
    private final RequestHandler requestHandler;

    public ClientHandlingThread(ProcessingThread indexProcessingThread, SocketWrapper clientSocketWrapper) {
        this.socketWrapper = clientSocketWrapper;
        this.requestHandler = new RequestHandler(indexProcessingThread);
    }

    @Override
    public void run() {
        socketWrapper.send(ResponseFactory.getServerReadyResponse());

        log.info("Started handling requests from client on port " + socketWrapper.getPort());

        while(true) {
            Request request = socketWrapper.read(Request.class);

            if(!(request instanceof CurrentProgressRequest)) {
                log.info("Request from client on port " + socketWrapper.getPort() + " " + request);
            }

            Optional<Response> optionalResponse = requestHandler.handleRequest(request);

            if(optionalResponse.isPresent()) {
                Response response = optionalResponse.get();
                if (response instanceof ExitResponse) {
                    break;
                } else {
                    socketWrapper.send(optionalResponse.get());
                }
            }
        }
        try {
            socketWrapper.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
