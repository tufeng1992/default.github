package com.study.test.aop.support;

import com.study.test.aop.framework.AopProxy;
import com.study.test.aop.framework.AopProxyFactory;
import com.study.test.aop.framework.DefaultAopProxyFactory;

public class ProxyFactory {

    private static AopProxyFactory aopProxyFactory = new DefaultAopProxyFactory();

    public static AopProxy getProxy(AdvisedSupport advisedSupport) {
        return aopProxyFactory.createAopProxy(advisedSupport);
    }
}
