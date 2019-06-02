package com.study.test.aop.framework;

public interface AopProxy {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}
