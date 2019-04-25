package com.study.test.beans.factory;

import com.study.test.beans.supports.STBeanDefinitionReader;
import com.study.test.beans.supports.STResourceLoader;

import java.util.List;

public abstract class STAbstractApplicationContext implements STApplicationContext {

    STResourceLoader resourceLoader;

    STBeanDefinitionReader stBeanDefinitionReader;

    void refrensh() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //scan config package
        List<Class<?>> classList = resourceLoader.scanConfiguration();
        for (Class<?> clazz : classList) {
            stBeanDefinitionReader.loadBeanDefinition(clazz);
        }
        //load beans
        stBeanDefinitionReader.loadAutowiredBeans();
        //load handlerMappings
        stBeanDefinitionReader.loadHandlerMappings();
        //load lazy bean
        stBeanDefinitionReader.lazyInitFields();
    }

}
