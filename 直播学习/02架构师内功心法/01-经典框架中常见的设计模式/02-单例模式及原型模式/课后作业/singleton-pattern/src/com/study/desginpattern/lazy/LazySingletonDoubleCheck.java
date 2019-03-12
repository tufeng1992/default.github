package com.study.desginpattern.lazy;

import java.io.Serializable;

/**
 * 双重校验控制的线程安全懒汉式单例
 */
public class LazySingletonDoubleCheck implements Serializable {

    private static final long serialVersionUID = -6204980102820183545L;
    private static LazySingletonDoubleCheck lazySingletonDoubleCheck;

    private LazySingletonDoubleCheck(){

    }

    public static LazySingletonDoubleCheck getInstance() {
        /**
         * 双重控制以及通过synchronized来控制线程安全的懒汉式单例加载
         */
        if (null == lazySingletonDoubleCheck) {
            synchronized (LazySingletonDoubleCheck.class) {
                if (null == lazySingletonDoubleCheck) {
                    lazySingletonDoubleCheck = new LazySingletonDoubleCheck();
                }
            }
        }
        return lazySingletonDoubleCheck;
    }
}
