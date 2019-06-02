package com.study.test.aop.aspect;

import com.study.test.aop.framework.Advice;
import com.study.test.aop.intercept.MethodInterceptor;
import com.study.test.aop.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class AfterReturningAdvice extends AbstractAspectJAdvice implements Advice, MethodInterceptor {

    private JoinPoint joinPoint;

    public AfterReturningAdvice(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object retVal = methodInvocation.proceed();
        this.joinPoint = methodInvocation;
        this.afterReturning(retVal, methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        return retVal;
    }

    public void afterReturning(Object returnValue, Method method, Object[] args,Object target) throws
            Throwable{
        invokeAdviceMethod(joinPoint,returnValue,null);
    }
}
