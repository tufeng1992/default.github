package com.study.test.beans.supports;


public class BeanWrapper {

    private Object wrapperInstance;

    private Class<?> wrapperClass;

    public BeanWrapper(Object wrapperInstance) {
        this.wrapperInstance = wrapperInstance;
        this.wrapperClass = wrapperInstance.getClass();
    }

    public Class<?> getWrapperClass() {
        return wrapperClass;
    }

    public Object getWrapperInstance() {
        return wrapperInstance;
    }
}
