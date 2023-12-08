package com.kpi.api.sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketWrapper implements AutoCloseable {
    private final Socket socket;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;
    private final int timeout = 200000;

    public SocketWrapper(Socket socket) {
        this.socket = socket;

        try {
            socket.setSoTimeout(timeout);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SocketWrapper(String host, int port) {
        try {
            socket = new Socket(host, port);
            socket.setSoTimeout(timeout);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T read(Class<T> requestClass) {
        try {
            return (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(Object data) {
        try {
            objectOutputStream.writeObject(data);
            objectOutputStream.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        closeResources();
    }

    private void closeResources() throws IOException {
        if (objectInputStream != null) {
            objectInputStream.close();
        } if (objectOutputStream != null) {
            objectOutputStream.close();
        } if (socket != null) {
            socket.close();
        }
    }

    public int getPort() {
        return socket.getPort();
    }

}
