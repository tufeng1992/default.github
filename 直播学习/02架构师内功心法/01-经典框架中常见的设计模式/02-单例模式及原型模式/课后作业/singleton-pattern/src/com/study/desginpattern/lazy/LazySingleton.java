package com.study.desginpattern.lazy;

/**
 * 基础懒汉式单例
 * 此模式存在很多问题：
 * 1.线程不安全。
 * 2.可能发生的序列化破坏。
 */
public class LazySingleton {
    private static LazySingleton lazySingleton;

    private LazySingleton(){}

    public static LazySingleton getInstance() {
        if (null == lazySingleton) {
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }
}
