package com.study.desginpattern.lazy;

/**
 * 防止反射暴力破坏单例
 * 对象实例化后依然可以通过java反射机制强行调用对象的私有构造函数从而得到一个新的实例
 * 在方法的构造函数中判断实例是否已经初始化，如果已经初始化则不允许
 */
public class LazySingletonReflect {

    private static LazySingletonReflect lazySingletonReflect;

    private static boolean flag = false;

    private LazySingletonReflect(){
        if (flag) {
            throw new RuntimeException("repeat build ");
        }
    }

    public static LazySingletonReflect getInstance() {
        /**
         * 双重控制以及通过synchronized来控制线程安全的懒汉式单例加载
         */
        if (null == lazySingletonReflect) {
            synchronized (LazySingletonReflect.class) {
                if (null == lazySingletonReflect) {
                    lazySingletonReflect = new LazySingletonReflect();
                    flag = true;
                }
            }
        }
        return lazySingletonReflect;
    }
}
