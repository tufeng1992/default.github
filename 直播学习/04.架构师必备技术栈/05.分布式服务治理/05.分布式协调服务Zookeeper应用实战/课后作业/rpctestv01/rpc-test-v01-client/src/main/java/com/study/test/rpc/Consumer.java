package com.study.test.rpc;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Consumer {

    private static int port;

    private static String host;

    private static CuratorFramework curatorFramework;

    public static final String PATH = "/provider/";


    public Consumer() {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1")
                .retryPolicy(new RetryNTimes(3, 1000))
                .connectionTimeoutMs(10000)
                .sessionTimeoutMs(60000)
                .namespace("rpctestv01")
                .build();
        curatorFramework.start();
    }

    public Object consume(RpcRequest rpcRequest) throws IOException {
        discover(rpcRequest);
        Socket socket = new Socket(host, port);
        return process(socket, rpcRequest);
    }

    private void discover(RpcRequest request) {
        try {
            String address = new String(curatorFramework.getData().forPath(PATH + request.getClassName()));
            String[] str = address.split(":");
            host = str[0];
            port = Integer.valueOf(str[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
