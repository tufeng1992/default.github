package com.study.plugins;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Invocation {

    private final Object target;

    private final Object[] args;

    private final Method method;


    public Invocation(Object target, Object[] args, Method method) {
        this.target = target;
        this.args = args;
        this.method = method;
    }

    public Object proceed() throws InvocationTargetException, IllegalAccessException {
        return this.method.invoke(target, args);
    }
}
