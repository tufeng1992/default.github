package com.study.test.aop.framework;

import com.study.test.aop.support.AdvisedSupport;

public interface AopProxyFactory {

    AopProxy createAopProxy(AdvisedSupport config);
}
