package com.study.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Hello world!
 *
 */
public class App
{
    private static ByteBuffer bb = ByteBuffer.allocate(1024);


    public static void main( String[] args ) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1", 8089);
        OutputStream out = socket.getOutputStream();
        TestEntity t = new TestEntity();
        t.setAge(18);
        out.write("问哈他".getBytes("utf-8"));
        InputStream in = socket.getInputStream();
        in.read(bb.array());
        in.close();
        out.close();
        String s = new String(bb.array());

        System.out.println(s);
        System.in.read();
    }
}
