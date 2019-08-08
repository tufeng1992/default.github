package com.study.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


public class TestNio {


    public static void main(String[] args) {
        TestNio t = new TestNio();
        t.listen(8089);
    }

    private Selector selector;

    //缓冲区大小
    private ByteBuffer sb = ByteBuffer.allocate(1024);

    public void listen(int port) {
        System.out.println("listen port : " + port + " start");
        try {
            //准备初始化通道
            ServerSocketChannel channel = ServerSocketChannel.open();
            //绑定一个地址
            channel.bind(new InetSocketAddress("127.0.0.1", port));
            //BIO升级NIO，默认采用的是阻塞模式
            channel.configureBlocking(false);
            //准备开始监控
            selector = Selector.open();
            //基于选择器的通道管理。允许开始io操作
            channel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                //扫描缓冲区准备好的事件
                selector.select();
                //拿到所有准备好的事件信息
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                //不断迭代就叫轮询
                //因为这里每次只能拿一个key，取一个状态处理，所以nio是同步的
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    process(key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void process(SelectionKey key){
        try {
            //针对不同的方法做出不同的响应
            if (key.isAcceptable()) {
                ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                //这里体现非阻塞，不论数据是否准备好给予状态反馈
                SocketChannel socketChannel = channel.accept();
                //设置为非阻塞
                socketChannel.configureBlocking(false);
                //当数据准备就绪将状态设置为可读
                key = socketChannel.register(selector, SelectionKey.OP_READ);
                System.out.println(key);
            } else if (key.isReadable()) {
                //从多路复用器中拿到客户端的引用
                SocketChannel channel = (SocketChannel) key.channel();
                int length = channel.read(sb);
                //表示读取到了数据
                if (length > 0) {
                    sb.flip();
                    String str = new String(sb.array(), "utf-8");
                    System.out.println("读取内容： " + str);
                    key = channel.register(selector, SelectionKey.OP_WRITE);
                    System.out.println(key);
                    key.attach(str);
                }
            } else if (key.isWritable()) {
                SocketChannel channel = (SocketChannel) key.channel();
                String str = (String) key.attachment();
                channel.write(ByteBuffer.wrap(("write:" + str).getBytes()));
                channel.close();
            }
        } catch (IOException e) {
            System.out.println("粗线异常啦！");
            System.out.println("异常内容：" + e.getMessage());
            key.cancel();
            e.printStackTrace();
        }
    }
}
