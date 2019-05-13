package com.study.test;

import com.study.annotations.Intercept;
import com.study.plugins.Interceptor;
import com.study.plugins.Invocation;
import com.study.plugins.Plugin;

@Intercept
public class PageHelperIntercept implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Exception {
        System.out.println("plugin do something");
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(this, target);
    }
}
