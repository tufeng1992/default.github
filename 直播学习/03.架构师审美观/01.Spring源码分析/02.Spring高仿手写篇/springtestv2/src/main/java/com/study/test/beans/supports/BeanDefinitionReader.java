package com.study.test.beans.supports;

import com.study.test.beans.factory.config.BeanDefinition;

public class BeanDefinitionReader{

    public BeanDefinitionReader(BeanDefinitionParseDelegate beanDefinitionParseDelegate) {
        this.beanDefinitionParseDelegate = beanDefinitionParseDelegate;
    }

    private BeanDefinitionParseDelegate beanDefinitionParseDelegate;

    /**
     * init load configuration under this path classes
     * @param clazz
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public BeanDefinition loadBeanDefinition(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        BeanDefinition beanDefinition = beanDefinitionParseDelegate.parseBean(clazz);
        return beanDefinition;
    }
}
