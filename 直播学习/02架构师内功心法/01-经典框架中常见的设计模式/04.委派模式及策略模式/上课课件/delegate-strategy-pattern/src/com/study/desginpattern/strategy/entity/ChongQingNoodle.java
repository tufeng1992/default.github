package com.study.desginpattern.strategy.entity;

/**
 * 重庆小面
 */
public class ChongQingNoodle implements Noodle {

    @Override
    public Noodle make() {
        System.out.println("开始制作重庆小面");
        ChongQingNoodle chongQingNoodle = new ChongQingNoodle();
        System.out.println("重庆小面做好了");
        return chongQingNoodle;
    }

    @Override
    public String toString() {
        return "重庆小面好吃!";
    }
}
