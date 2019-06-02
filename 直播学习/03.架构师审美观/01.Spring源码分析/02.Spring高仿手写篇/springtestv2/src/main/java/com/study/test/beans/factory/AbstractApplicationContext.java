package com.study.test.beans.factory;



public abstract class AbstractApplicationContext implements ApplicationContext {

    void refresh() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //scan config package
        scanConfiguration();
        //do load beanDefinitions
        loadBeanDefinition();
    }

    private void scanConfiguration() {
        doLoadApplicationConfig();
    }

    protected abstract void doLoadApplicationConfig();

    private void loadBeanDefinition() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        doLoadBeanDefinition();
    }

    protected abstract void doLoadBeanDefinition() throws ClassNotFoundException, InstantiationException, IllegalAccessException;

}
