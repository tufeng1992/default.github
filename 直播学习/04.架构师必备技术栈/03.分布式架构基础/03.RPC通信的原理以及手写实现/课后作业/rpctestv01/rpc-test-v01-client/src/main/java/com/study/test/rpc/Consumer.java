package com.study.test.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Consumer {

    private final int port;

    private final String host;


    public Consumer(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public Object consume(RpcRequest rpcRequest) throws IOException {
        Socket socket = new Socket(host, port);
        return process(socket, rpcRequest);
    }

    private Object process(Socket socket, RpcRequest rpcRequest){
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        Object obj = null;
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(rpcRequest);
            inputStream = new ObjectInputStream(socket.getInputStream());
            obj = inputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != outputStream) {
                    outputStream.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }
}
