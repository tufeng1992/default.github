package com.study.test.rpc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Publisher {


    private final int port;

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public Publisher(int port) {
        this.port = port;
    }


    public void publish(Object inf) throws IOException {
        ServerSocket socket = new ServerSocket(port);
        while (true) {
            Socket s = socket.accept();
            process(s, inf);
        }
    }


    private void process(Socket socket, Object inf) {
        RpcRequestHandler rpcRequestHandler = new RpcRequestHandler(socket, inf);
        rpcRequestHandler.run();
    }
}
