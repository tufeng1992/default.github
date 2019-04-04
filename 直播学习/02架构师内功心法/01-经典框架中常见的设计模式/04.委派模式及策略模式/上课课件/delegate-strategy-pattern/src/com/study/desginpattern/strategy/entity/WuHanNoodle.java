package com.study.desginpattern.strategy.entity;

/**
 * 武汉热干面
 */
public class WuHanNoodle implements Noodle {
    @Override
    public Noodle make() {
        System.out.println("开始制作武汉热干面");
        WuHanNoodle wuHanNoodle = new WuHanNoodle();
        System.out.println("武汉热干面做好了");
        return wuHanNoodle;
    }

    @Override
    public String toString() {
        return "武汉热干面好吃!";
    }
}
