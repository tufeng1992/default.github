package com.study.test.rpc;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Publisher {


    private final int port;

    private static CuratorFramework curatorFramework;

    public static final String PATH = "/provider/";

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public Publisher(int port) {
        this.port = port;
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1")
                .retryPolicy(new RetryNTimes(3, 1000))
                .connectionTimeoutMs(10000)
                .sessionTimeoutMs(60000)
                .namespace("rpctestv01")
                .build();
        curatorFramework.start();
    }


    public void publish(Object inf) throws IOException {
        ServerSocket socket = new ServerSocket(port);
        registerServer(inf);
        while (true) {
            Socket s = socket.accept();
            process(s, inf);
        }
    }


    private void process(Socket socket, Object inf) {
        RpcRequestHandler rpcRequestHandler = new RpcRequestHandler(socket, inf);
        rpcRequestHandler.run();
    }

    /**
     * 注册服务
     * @param inf
     */
    private void registerServer(Object inf) {
        try {
            String childPath = PATH + inf.getClass().getGenericInterfaces()[0].getTypeName();
            String address = "127.0.0.1:" + port;
            if (curatorFramework.checkExists().forPath(childPath) != null) {
                curatorFramework.getChildren().forPath(childPath).add(0, address);
            } else {
                curatorFramework.create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(childPath, address.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
