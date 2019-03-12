package com.study.desginpattern.hungry;

/**
 * 实现饿汉式单例模式
 */
public class HungrySingle {
    /**
     * 实例对象
     * 这里加入final避免反射改变对象
     */
    private static final HungrySingle hungrySingle = new HungrySingle();

    private HungrySingle(){}

    public static HungrySingle getInstance() {
        return hungrySingle;
    }
}
