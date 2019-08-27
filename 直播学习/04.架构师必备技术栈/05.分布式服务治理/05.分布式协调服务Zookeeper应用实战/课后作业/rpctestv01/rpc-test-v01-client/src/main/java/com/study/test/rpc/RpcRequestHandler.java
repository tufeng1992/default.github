package com.study.test.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RpcRequestHandler implements InvocationHandler {

    private final int port;

    private final String host;


    public RpcRequestHandler(int port, String host) {
        this.port = port;
        this.host = host;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Consumer consumer = new Consumer();
        RpcRequest rpcRequest = new RpcRequest(method.getDeclaringClass().getName(), method.getName(), args);
        return consumer.consume(rpcRequest);
    }
}
