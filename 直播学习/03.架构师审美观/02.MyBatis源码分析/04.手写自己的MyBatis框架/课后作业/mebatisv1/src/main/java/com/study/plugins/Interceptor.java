package com.study.plugins;


public interface Interceptor {

    Object intercept(Invocation invocation) throws Exception;

    Object plugin(Object target);

}
