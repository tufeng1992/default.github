package com.study.test.beans.factory;

import com.study.test.beans.supports.STBeanDefinitionParseDelegate;
import com.study.test.beans.supports.STBeanDefinitionReader;
import com.study.test.beans.supports.STResourceLoader;
import com.study.test.beans.supports.STServletHandler;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;

public class STWebApplicationContext extends STAbstractApplicationContext {

    public void refrensh(ServletConfig config) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        this.resourceLoader = new STResourceLoader();
        this.stBeanDefinitionReader = new STBeanDefinitionReader(new STBeanDefinitionParseDelegate());
        //load config file
        this.resourceLoader.loadConfiguration(config);
        super.refrensh();
    }

    public STServletHandler getHandler(HttpServletRequest request) {
        return this.stBeanDefinitionReader.doGetHandler(request);
    }

    @Override
    public Object getBean(String id) {
        return stBeanDefinitionReader.getBean(id);
    }

    @Override
    public Object getType(String type) {
        return stBeanDefinitionReader.getType(type);
    }

}
