package com.study.desginpattern.dynamic.entity;


import com.study.desginpattern.dynamic.proxy.TClassLoader;
import com.study.desginpattern.dynamic.proxy.TInvocationHandler;
import com.study.desginpattern.dynamic.proxy.TProxy;

import java.lang.reflect.Method;

public class TMeipo implements TInvocationHandler {

    private Object target;

    public Object getInstance(Object target) throws Exception {
        this.target = target;
        return TProxy.newProxyInstance(new TClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {

        System.out.println("我是媒婆：得给你找个异性才行");
        System.out.println("开始进行海选...");
        System.out.println("------------");
        method.invoke(this.target, args);
        System.out.println("开始执行");
        return null;
    }
}
