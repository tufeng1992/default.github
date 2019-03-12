package com.study.desginpattern.lazy;

import java.io.Serializable;

/**
 * 防止序列化破坏的懒汉式单例
 */
public class LazySingletonSerialize implements Serializable {
    private static final long serialVersionUID = 5630642347803990867L;

    private static LazySingletonSerialize lazySingletonSerialize;

    private LazySingletonSerialize(){

    }

    public static LazySingletonSerialize getInstance() {
        if (null == lazySingletonSerialize) {
            synchronized (LazySingletonSerialize.class) {
                if (null == lazySingletonSerialize) {
                    lazySingletonSerialize = new LazySingletonSerialize();
                }
            }
        }
        return lazySingletonSerialize;
    }

    private Object readResolve() {
        return lazySingletonSerialize;
    }
}
