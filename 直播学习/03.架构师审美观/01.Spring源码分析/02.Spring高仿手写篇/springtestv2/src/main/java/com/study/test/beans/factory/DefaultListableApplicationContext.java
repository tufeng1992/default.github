package com.study.test.beans.factory;

import com.study.test.aop.framework.SimpleBeanPostProcessor;
import com.study.test.aop.support.AdvisedSupport;
import com.study.test.aop.support.AopConfig;
import com.study.test.aop.support.ProxyFactory;
import com.study.test.beans.factory.config.BeanDefinition;
import com.study.test.beans.factory.config.BeanPostProcessor;
import com.study.test.beans.supports.*;
import com.study.test.core.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableApplicationContext extends AbstractApplicationContext {

    protected BeanDefinitionReader beanDefinitionReader;

    protected final Map<String, BeanWrapper> beanWrapperMap = new ConcurrentHashMap<>();

    protected final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    protected List<BeanDefinition> beanDefinitions = new ArrayList<>();

    private Map<String, Object> notLoadBeans = new ConcurrentHashMap<>();

    private ResourceLoader resourceLoader;

    protected Map<String, Object> applicationConfig;

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    @Override
    public Object getBean(String beanName) {
        return doGetBean(beanName);
    }

    @Override
    public Object getType(String type) {
        return null;
    }

    @Override
    protected void doLoadApplicationConfig() {
        resourceLoader = new ResourceLoader();
        resourceLoader.loadConfiguration(applicationConfig);
    }

    @Override
    protected void doLoadBeanDefinition() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<Class<?>> classList = resourceLoader.scanConfiguration();
        beanDefinitionReader = new BeanDefinitionReader(new BeanDefinitionParseDelegate());
        for (Class<?> clazz : classList) {
            BeanDefinition beanDefinition = beanDefinitionReader.loadBeanDefinition(clazz);
            if (null != beanDefinition) {
                //do register beanDefinitions
                beanDefinitions.add(beanDefinition);
            }
        }
    }

    protected void populateBean(String beanName, Object obj) throws IllegalAccessException {
        try {
            //load beans
            doAutowiredBeans(beanName, obj);
            //load lazy bean
            lazyInitFields();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public BeanDefinition getBeanDefinition(String beanName) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (beanDefinition.getFactoryBeanName().equals(beanName)) {
                return beanDefinition;
            }
        }
        return null;
    }

    public List<BeanDefinition> getAllBeanDefinition() {
        return beanDefinitions;
    }


    /**
     * init load want autowired beans for this initialized beanDefinitions
     * @throws IllegalAccessException
     */
    public void doAutowiredBeans(String beanName, Object obj) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        doLoadFields(obj);
    }



    /**
     * init have no time to initialized beans
     * @throws IllegalAccessException
     */
    public void lazyInitFields() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        for (String key : notLoadBeans.keySet()) {
            Object obj = notLoadBeans.get(key);
            for (Field field : obj.getClass().getDeclaredFields()) {
                if (field.getName().equals(key)) {
                    Object fieldObj = getBean(field.getName());
                    System.out.println(fieldObj);
                    field.setAccessible(true);
                    field.set(obj, fieldObj);
                }
            }
        }
        notLoadBeans.clear();
    }


    private void doLoadFields(Object object) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (null != field.getAnnotation(Autowired.class)) {
                Object fieldObj = getBean(field.getName());
                if (null != fieldObj) {
                    field.setAccessible(true);
                    field.set(object, fieldObj);
                } else {
                    notLoadBeans.put(field.getName(), object);
                }
            }
        }
    }

    private Object doGetBean(String beanName) {
        Object obj = singletonObjects.get(beanName);
        if (obj == null) {
            obj = createBean(beanName);
        }
        return obj;
    }

    private Object createBean(String beanName) {
        try {
            BeanDefinition beanDefinition = getBeanDefinition(beanName);
            return doCreateBean(beanDefinition);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }


    protected Object doCreateBean(BeanDefinition beanDefinition) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Object obj = instantiateBean(beanDefinition);
        BeanPostProcessor beanPostProcessor = new SimpleBeanPostProcessor();
        //初始化前调用
        beanPostProcessor.postProcessBeforeInitialization(obj, beanDefinition.getFactoryBeanName());
        populateBean(beanDefinition.getFactoryBeanName(), obj);
        //初始化后调用
        beanPostProcessor.postProcessAfterInitialization(obj, beanDefinition.getFactoryBeanName());
        return obj;
    }

    private Object instantiateBean(BeanDefinition beanDefinition ) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Object obj = null;
        if (beanWrapperMap.get(beanDefinition.getFactoryBeanName()) == null) {
            Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
            obj = clazz.newInstance();
            AdvisedSupport advisedSupport = instantionAopConfig(beanDefinition);
            advisedSupport.setTargetClass(clazz);
            advisedSupport.setTarget(obj);

            if (advisedSupport.pointCutMatch()) {
                obj = ProxyFactory.getProxy(advisedSupport).getProxy();
            }
            beanWrapperMap.put(beanDefinition.getFactoryBeanName(), new BeanWrapper(obj));
        } else {
            obj = beanWrapperMap.get(beanDefinition.getFactoryBeanName()).getWrapperInstance();
        }
        return obj;
    }

    private AdvisedSupport instantionAopConfig(BeanDefinition beanDefinition){
        AopConfig config = new AopConfig();
        config.setPointCut(resourceLoader.getProperties().getProperty("pointCut"));
        config.setAspectClass(resourceLoader.getProperties().getProperty("aspectClass"));
        config.setAspectBefore(resourceLoader.getProperties().getProperty("aspectBefore"));
        config.setAspectAfter(resourceLoader.getProperties().getProperty("aspectAfter"));
        config.setAspectAfterThrow(resourceLoader.getProperties().getProperty("aspectAfterThrow"));
        config.setAspectAfterThrowingName(resourceLoader.getProperties().getProperty("aspectAfterThrowingName"));
        return new AdvisedSupport(config);
    }
}
