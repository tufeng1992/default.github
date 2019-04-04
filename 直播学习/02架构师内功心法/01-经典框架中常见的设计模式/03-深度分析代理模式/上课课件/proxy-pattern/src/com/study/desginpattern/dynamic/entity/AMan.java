package com.study.desginpattern.dynamic.entity;

/**
 * 代理模式下的测试类
 */
public class AMan implements Person {

    @Override
    public void findObject() {
        System.out.println("this people want find friends!!!");
    }
}
