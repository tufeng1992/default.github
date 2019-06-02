package com.study.test.aop.framework;

import com.study.test.aop.intercept.MethodInvocation;
import com.study.test.aop.support.AdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private AdvisedSupport advisedSupport;

    public JdkDynamicAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        return getProxy(advisedSupport.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader, advisedSupport.getTargetClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> interceptorsAndDynamicMethodMatchers =
                advisedSupport.getInterceptorsAndDynamicInterceptionAdvise(method,this.advisedSupport.getTargetClass());
        MethodInvocation invocation = new
                MethodInvocation(proxy, method, this.advisedSupport.getTarget(), this.advisedSupport.getTargetClass(), args,
                interceptorsAndDynamicMethodMatchers);
        return invocation.proceed();
    }
}
