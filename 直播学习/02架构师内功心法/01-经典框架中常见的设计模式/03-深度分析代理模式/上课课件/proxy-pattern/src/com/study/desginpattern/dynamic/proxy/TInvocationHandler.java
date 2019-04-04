package com.study.desginpattern.dynamic.proxy;

import java.lang.reflect.Method;

public interface TInvocationHandler {


    /**
     * 手动实现java动态代理需要的InvocationHandler
     *
     * @param proxy  代理对象
     * @param method 代理执行函数
     * @param args   函数对应参数
     * @return
     * @throws Exception
     */
    Object invoke(Object proxy, Method method, Object[] args) throws Exception;
}
