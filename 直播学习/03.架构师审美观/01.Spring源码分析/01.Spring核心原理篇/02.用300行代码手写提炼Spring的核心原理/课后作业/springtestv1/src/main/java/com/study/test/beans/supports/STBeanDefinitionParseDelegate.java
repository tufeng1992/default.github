package com.study.test.beans.supports;

import com.study.test.core.annotation.*;


/**
 * beanDefinition parse executor
 * @author tf
 */
public class STBeanDefinitionParseDelegate {

    /**
     * parse a bean entity
     * @param clazz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public BeanDefinition parseBean(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        BeanDefinition beanDefinition = null;
        if (null != clazz.getAnnotation(STController.class)) {
            Object obj = clazz.newInstance();
            beanDefinition = doParseControllerBean(obj);
        } else if (null != clazz.getAnnotation(STService.class)) {
            Object obj = clazz.newInstance();
            beanDefinition = doParseServiceBean(obj, clazz.getAnnotation(STService.class));
        } else if (null != clazz.getAnnotation(STComponent.class)) {
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
        BeanDefinition beanDefinition = new BeanDefinition(toLowcase(obj.getClass().getSimpleName()), obj.getClass().getTypeName(), obj);
        return beanDefinition;
    }

    /**
     * parse type for service bean
     * @param obj service bean
     * @param stService
     * @return beanDefinition entity
     */
    private BeanDefinition doParseServiceBean(Object obj, STService stService) {
        String beanName = stService.name() != "" ? stService.name() : toLowcase(obj.getClass().getSimpleName());
        BeanDefinition beanDefinition = new BeanDefinition(beanName, obj.getClass().getTypeName(), obj);
        return beanDefinition;
    }

    /**
     * parse type for component bean
     * @param obj component bean
     * @return beanDefinition entity
     */
    private BeanDefinition doParseComponentBean(Object obj) {
        BeanDefinition beanDefinition = new BeanDefinition(toLowcase(obj.getClass().getSimpleName()), obj.getClass().getTypeName(), obj);
        return beanDefinition;
    }


    private static String toLowcase(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return new String(chars);
    }
}
