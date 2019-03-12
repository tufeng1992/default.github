package com.study.desginpattern.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册式单例
 */
public class RegistrySingleton {

    private Map<String, Object> registryMap = new ConcurrentHashMap<>();

    private RegistrySingleton(){

    }
    public static RegistrySingleton getInstance() {
        return RegistrySingletonHolder.registrySingleton;
    }

    public Object getBean(String beanName) {
        return registryMap.get(beanName);
    }

    public void setBean(String beanName, Class<?> clazz){
        try {
            if (null == registryMap.get(beanName)) {
                synchronized (this) {
                    if (null == registryMap.get(beanName)) {
                        registryMap.put(beanName, clazz.newInstance());
                    }
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static final class RegistrySingletonHolder {
         private static final RegistrySingleton registrySingleton = new RegistrySingleton();
    }
}
