package com.study.test.rpc;

import java.lang.reflect.Proxy;

public class RpcProxyClient {

    public static <T> T rpcProxy(Class<T> clazz, String host, int port) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class<?>[]{clazz}, new RpcRequestHandler(port, host));
    }

}
