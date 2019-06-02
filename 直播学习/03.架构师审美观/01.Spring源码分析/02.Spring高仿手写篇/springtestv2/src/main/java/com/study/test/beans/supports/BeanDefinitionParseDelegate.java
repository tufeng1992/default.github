package com.study.test.beans.supports;

import com.study.test.beans.factory.config.BeanDefinition;
import com.study.test.core.annotation.Component;
import com.study.test.core.annotation.Controller;
import com.study.test.core.annotation.Service;


/**
 * beanDefinition parse executor
 * @author tf
 */
public class BeanDefinitionParseDelegate {

    /**
     * parse a bean entity
     * @param clazz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public BeanDefinition parseBean(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        BeanDefinition beanDefinition = null;
        if (null != clazz.getAnnotation(Controller.class)) {
            Object obj = clazz.newInstance();
            beanDefinition = doParseControllerBean(obj);
        } else if (null != clazz.getAnnotation(Service.class)) {
            Object obj = clazz.newInstance();
            beanDefinition = doParseServiceBean(obj, clazz.getAnnotation(Service.class));
        } else if (null != clazz.getAnnotation(Component.class)) {
            Object obj = clazz.newInstance();
            beanDefinition = doParseComponentBean(obj);
        }
        return beanDefinition;
    }

    /**
     * parse type for controller bean
     * @param obj controller bean
     * @return beanDefinition entity
     */
    private BeanDefinition doParseControllerBean(Object obj) {
        BeanDefinition beanDefinition = new DefaultBeanDefinition(obj.getClass().getName(), toLowcase(obj.getClass().getSimpleName()));
        return beanDefinition;
    }

    /**
     * parse type for service bean
     * @param obj service beans
     * @param service
     * @return beanDefinition entity
     */
    private BeanDefinition doParseServiceBean(Object obj, Service service) {
        String beanName = service.name() != "" ? service.name() : toLowcase(obj.getClass().getSimpleName());
        BeanDefinition beanDefinition = new DefaultBeanDefinition(obj.getClass().getName(), beanName);
        return beanDefinition;
    }

    /**
     * parse type for component bean
     * @param obj component bean
     * @return beanDefinition entity
     */
    private BeanDefinition doParseComponentBean(Object obj) {
        BeanDefinition beanDefinition = new DefaultBeanDefinition(obj.getClass().getName(), toLowcase(obj.getClass().getSimpleName()));
        return beanDefinition;
    }


    private static String toLowcase(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return new String(chars);
    }
}
