package com.study.test.beans.supports;

import com.study.test.beans.factory.STAbstractApplicationContext;
import com.study.test.core.annotation.*;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class STBeanDefinitionReader extends STAbstractApplicationContext {

    private List<HandlerMapping> handlerMappings = new ArrayList<>();

    private List<BeanDefinition> beanDefinitions = new ArrayList<>();

    private Map<String, Object> notLoadBeans = new ConcurrentHashMap<>();

    public STBeanDefinitionReader(STBeanDefinitionParseDelegate stBeanDefinitionParseDelegate) {
        this.beanDefinitionParseDelegate = stBeanDefinitionParseDelegate;
    }

    private STBeanDefinitionParseDelegate beanDefinitionParseDelegate;

    /**
     * init load configuration under this path classes
     * @param clazz
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void loadBeanDefinition(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        BeanDefinition beanDefinition = beanDefinitionParseDelegate.parseBean(clazz);
        if (null != beanDefinition) {
            beanDefinitions.add(beanDefinition);
        }
    }

    /**
     * init load want autowired beans for this initialized beanDefinitions
     * @throws IllegalAccessException
     */
    public void loadAutowiredBeans() throws IllegalAccessException {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            doLoadFields(beanDefinition.getObject());
        }
    }

    /**
     * init load handlerMappings for this initialized beanDefinitions
     */
    public void loadHandlerMappings() {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            Class<?> clazz = beanDefinition.getObject().getClass();
            if (null != clazz.getAnnotation(STController.class)) {
                StringBuffer path = new StringBuffer();
                if (null != clazz.getAnnotation(STRequestMapping.class)) {
                    path.append("/" + clazz.getAnnotation(STRequestMapping.class).path());
                }
                doLoadMethods(beanDefinition.getObject(), clazz.getDeclaredMethods(), path);
            }
        }
    }

    /**
     * init have no time to initialized beans
     * @throws IllegalAccessException
     */
    public void lazyInitFields() throws IllegalAccessException {
        for (String key : notLoadBeans.keySet()) {
            Object obj = notLoadBeans.get(key);
            for (Field field : obj.getClass().getDeclaredFields()) {
                if (field.getName().equals(key)) {
                    BeanDefinition beanDefinition = getBeanDefinition(field.getName());
                    System.out.println(beanDefinition);
                    field.setAccessible(true);
                    field.set(obj, beanDefinition.getObject());
                }
            }
        }
    }


    private void doLoadFields(Object object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (null != field.getAnnotation(STAutowired.class)) {
                System.out.println(field.getName());
                BeanDefinition beanDefinition = getBeanDefinition(field.getName());
                System.out.println(beanDefinition);
                if (null != beanDefinition) {
                    field.setAccessible(true);
                    field.set(object, beanDefinition.getObject());
                } else {
                    notLoadBeans.put(field.getName(), object);
                }
            }
        }
    }

    private void doLoadMethods(Object obj, Method[] methods, StringBuffer path) {
        for (Method method : methods) {
            if (null != method.getAnnotation(STRequestMapping.class)) {
                HandlerMapping handlerMapping = new HandlerMapping();
                String urlPath = path.toString() + "/" + method.getAnnotation(STRequestMapping.class).path();
                handlerMapping.setArgs(method.getParameters());
                handlerMapping.setPath(urlPath);
                handlerMapping.setMethod(method);
                handlerMapping.setBean(obj);
                doLoadParameter(method, handlerMapping);
                handlerMappings.add(handlerMapping);
            }
        }
    }

    private String[] doLoadParameter(Method method, HandlerMapping handlerMapping) {
        String[] parameterName = new String[method.getParameterAnnotations().length];
        for (int i = 0; i < method.getParameterAnnotations().length; i++) {

            Annotation[] annotation = method.getParameterAnnotations()[i];
            if (annotation[0].annotationType().equals(STRequestParam.class)){
                parameterName[i] = ((STRequestParam)annotation[0]).name();
            }
        }
        handlerMapping.setParameterNames(parameterName);
        return null;
    }

    private BeanDefinition getBeanDefinition(String beanName) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (beanDefinition.getBeanName().equals(beanName)) {
                return beanDefinition;
            }
        }
        return null;
    }

    private HandlerMapping getHandlerMapping(String path) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        for (HandlerMapping handlerMapping : handlerMappings) {
            if (handlerMapping.getPath().equals(path)) {
                return handlerMapping;
            }
        }
        return null;
    }

    public STServletHandler doGetHandler(HttpServletRequest request) {
        String url = request.getRequestURI();
        System.out.println(url);
        url = url.replace(request.getContextPath(), "").replaceAll("/+", "/");
        HandlerMapping handlerMapping = getHandlerMapping(url);
        if (null != handlerMapping) {
            return new STServletHandler(handlerMapping);
        }
        return null;
    }


    @Override
    public Object getBean(String id) {
        return getBeanDefinition(id);
    }

    @Override
    public Object getType(String type) {
        return null;
    }
}
