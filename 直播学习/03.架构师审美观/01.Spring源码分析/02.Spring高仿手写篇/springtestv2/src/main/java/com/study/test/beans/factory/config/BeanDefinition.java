package com.study.test.beans.factory.config;

public interface BeanDefinition {

    String getBeanClassName();

    void setBeanClassName(String beanClassName);

    boolean isLazyInit();

    void setLazyInit(boolean lazyInit);

    String getFactoryBeanName();

    void setFactoryBeanName(String factoryBeanName);
}
