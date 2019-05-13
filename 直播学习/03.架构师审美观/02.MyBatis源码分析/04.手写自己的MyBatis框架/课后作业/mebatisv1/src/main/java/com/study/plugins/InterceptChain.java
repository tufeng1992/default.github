package com.study.plugins;

import java.util.List;

public class InterceptChain {

    private List<Interceptor> intercepts;

    public InterceptChain(List<Interceptor> intercepts) {
        this.intercepts = intercepts;
    }

    public Object pluginAll(Object target) {
        for (Interceptor interceptor : intercepts) {
            target = interceptor.plugin(target);
        }
        return target;
    }
}
