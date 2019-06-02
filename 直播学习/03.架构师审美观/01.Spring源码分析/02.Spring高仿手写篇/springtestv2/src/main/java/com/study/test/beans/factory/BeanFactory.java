package com.study.test.beans.factory;

public interface BeanFactory {


    Object getBean(String beanName);

    Object getType(String type);


}
