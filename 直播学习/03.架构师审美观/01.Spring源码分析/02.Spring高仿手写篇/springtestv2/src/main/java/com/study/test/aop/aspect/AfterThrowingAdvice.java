package com.study.test.aop.aspect;

import com.study.test.aop.framework.Advice;
import com.study.test.aop.intercept.MethodInterceptor;
import com.study.test.aop.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class AfterThrowingAdvice extends AbstractAspectJAdvice implements Advice, MethodInterceptor {

    private String throwingName;

    private MethodInvocation mi;

    public AfterThrowingAdvice(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    public void setThrowingName(String name) {
        this.throwingName = name;
    }


    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            return methodInvocation.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            invokeAdviceMethod(methodInvocation, null, throwable.getCause());
            throw throwable;
        }
    }
}
