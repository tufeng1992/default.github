package com.study.test.aop.intercept;

public interface MethodInterceptor {

    Object invoke(MethodInvocation methodInvocation) throws Throwable;
}
