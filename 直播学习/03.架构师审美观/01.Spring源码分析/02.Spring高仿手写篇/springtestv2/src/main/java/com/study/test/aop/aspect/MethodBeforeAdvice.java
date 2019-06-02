package com.study.test.aop.aspect;

import com.study.test.aop.framework.Advice;
import com.study.test.aop.intercept.MethodInterceptor;
import com.study.test.aop.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class MethodBeforeAdvice extends AbstractAspectJAdvice implements Advice, MethodInterceptor {

    private JoinPoint joinPoint;


    public MethodBeforeAdvice(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    public void before(Method method, Object[] args, Object target) throws Throwable {
        invokeAdviceMethod(this.joinPoint,null,null);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        this.joinPoint = mi;
        this.before(mi.getMethod(), mi.getArguments(), mi.getThis());
        return mi.proceed();
    }
}
