package com.study.test.beans.factory.config;


public interface BeanPostProcessor {

    //为在Bean的初始化前提供回调入口
    default Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    //为在Bean的初始化之后提供回调入口
    default Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
