package com.study.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestAioServer {


    public static void main(String[] args) {
        TestAioServer testAio = new TestAioServer();
        testAio.listen("127.0.0.1", 6669);
    }


    public void listen(String host, int port) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            AsynchronousChannelGroup channelGroup =
                    AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
            //注册channel，监听回调、响应事件信息
            final AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open(channelGroup);
            serverSocketChannel.bind(new InetSocketAddress(host, port));
            System.out.println("启动服务，服务地址：" + host + ":" + port);
            final ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            //accept数据
            serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                //基于epoll io多路复用模型实现的事件响应，由操作系统来触发，程序监听事件信息状态
                @Override
                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    System.out.println("收到准备完成事件");
                    try {
                        buffer.clear();
                        result.read(buffer).get();
                        buffer.flip();
                        result.write(buffer);
                        buffer.flip();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            result.close();
                            serverSocketChannel.accept(null, this);
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    }
                    System.out.println("操作成功");

                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("IO操作失败: " + exc);
                }
            });
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
