package com.study.test.aop.framework;

import com.study.test.aop.support.AdvisedSupport;

public class DefaultAopProxyFactory implements AopProxyFactory {

    @Override
    public AopProxy createAopProxy(AdvisedSupport config) {
        if (config.getTargetClass().getInterfaces().length > 0) {
            return new JdkDynamicAopProxy(config);
        }
        return new CglibAopProxy(config);
    }
}
