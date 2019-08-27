package com.study.test;

import com.study.test.rpc.RpcProxyClient;
import com.study.test.service.IUserService;
import com.study.test.service.SayHelloServiceImpl;

/**
 * Hello world!
 *
 */
public class ClientApp
{
    public static void main( String[] args )
    {
        String host = "127.0.0.1";
        int port = 20880;
        IUserService userService = RpcProxyClient.rpcProxy(IUserService.class, host, port);
        SayHelloServiceImpl sayHelloService = new SayHelloServiceImpl(userService);

        sayHelloService.sayHello("hello");

        sayHelloService.sayListen("listen");

    }
}
