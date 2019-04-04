package com.study.desginpattern.strategy.entity;

/**
 * 兰州拉面
 */
public class LanZhouNoodle implements Noodle {

    @Override
    public Noodle make() {
        System.out.println("开始制作兰州拉面");
        LanZhouNoodle lanZhouNoodle = new LanZhouNoodle();
        System.out.println("兰州拉面做好了");
        return lanZhouNoodle;
    }

    @Override
    public String toString() {
        return "兰州拉面好吃!";
    }
}
