package com.study.test;

import com.study.test.rpc.Publisher;
import com.study.test.service.UserServiceImpl;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws IOException {
        Publisher publisher = new Publisher(20880);
        UserServiceImpl userService = new UserServiceImpl();
        publisher.publish(userService);
        System.out.println( "Hello World!" );
        System.in.read();
    }
}
