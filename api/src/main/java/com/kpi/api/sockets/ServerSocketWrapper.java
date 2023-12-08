package com.kpi.api.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class ServerSocketWrapper implements AutoCloseable {

    private final ServerSocket serverSocket;

    public ServerSocketWrapper(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<SocketWrapper> connect() {
        try {
            Socket socket = serverSocket.accept();
            return Optional.of(new SocketWrapper(socket));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}

