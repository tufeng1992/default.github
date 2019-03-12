package com.study.desginpattern.lazy;

import java.io.Serializable;

/**
 * 简单的通过静态内部类的方式实现懒汉式单例
 */
public class SimpleLazySingleton implements Serializable {

    private SimpleLazySingleton(){

    }

    public static SimpleLazySingleton getInstance() {
        return SimpleLazySingletonHolder.simpleLazySingleton;
    }

    private Object readResolve() {
        return SimpleLazySingletonHolder.simpleLazySingleton;
    }

    /**
     * 单例对象持有者
     * 通过java的加载机制实现懒汉式单例
     * java在处理类的调用时，会先实例化类的内部类，而实例化内部类的时候会实例化单例对象
     * 以此实现懒汉式的单例，而且不存在线程安全问题
     */
    private static final class SimpleLazySingletonHolder {
        private static final SimpleLazySingleton simpleLazySingleton = new SimpleLazySingleton();
    }

}
