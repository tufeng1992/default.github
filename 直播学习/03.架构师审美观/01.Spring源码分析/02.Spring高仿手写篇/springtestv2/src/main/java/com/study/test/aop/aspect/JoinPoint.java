package com.study.test.aop.aspect;

import java.lang.reflect.Method;

public interface JoinPoint {


    Method getMethod();

    Object[] getArguments();

    Object getThis();
}
