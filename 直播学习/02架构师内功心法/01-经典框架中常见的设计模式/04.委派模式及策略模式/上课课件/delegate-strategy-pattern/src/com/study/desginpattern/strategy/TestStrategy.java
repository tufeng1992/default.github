package com.study.desginpattern.strategy;

import com.study.desginpattern.strategy.entity.Noodle;

public class TestStrategy {

    public static void main(String[] args) {
        NoodleRestaurant noodleRestaurant = new NoodleRestaurant();
        System.out.println("客户1开始点餐");
        String name1 = "CQ";
        System.out.println("来一份:" + name1);
        Noodle noodle1 = noodleRestaurant.order(name1);
        System.out.println(noodle1);

        System.out.println("客户2开始点餐");
        String name2 = "WH";
        System.out.println("来一份:" + name2);
        Noodle noodle2 = noodleRestaurant.order(name2);
        System.out.println(noodle2);

        System.out.println("客户3开始点餐");
        String name3 = "LZ";
        System.out.println("来一份:" + name3);
        Noodle noodle3 = noodleRestaurant.order(name3);
        System.out.println(noodle3);
    }
}
