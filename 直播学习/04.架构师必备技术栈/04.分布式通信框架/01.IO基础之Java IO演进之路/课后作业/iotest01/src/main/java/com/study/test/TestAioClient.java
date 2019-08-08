package com.study.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

public class TestAioClient {

    private final AsynchronousSocketChannel socketChannel;

    public TestAioClient() throws IOException {
        this.socketChannel = AsynchronousSocketChannel.open();
    }

    public static void main(String[] args) throws IOException {
        TestAioClient client = new TestAioClient();
        client.connect("127.0.0.1", 6669);
        System.in.read();
    }


    public void connect(String host, int port) {
        //连接到服务并注册事件回调
        socketChannel.connect(new InetSocketAddress(host, port), null, new CompletionHandler<Void, Object>() {
            @Override
            public void completed(Void result, Object attachment) {
                //成功后发送信息
                try {
                    Integer writeRes = socketChannel.write(ByteBuffer.wrap("来啦老弟！".getBytes())).get();
                    System.out.println("writeRes:" + writeRes);
                    System.out.println("发送成功");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("发送失败");
            }
        });
        final ByteBuffer bb = ByteBuffer.allocate(1024);
        //注册读取事件信息
        socketChannel.read(bb, null, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer result, Object attachment) {
                System.out.println("收到事件响应");
                System.out.println("信息内容：" + new String(bb.array()));

            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("接收失败");
            }
        });
    }
}
