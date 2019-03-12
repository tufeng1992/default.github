package com.study.desginpattern.hungry;

public class HungryTest {

    public static void main(String[] args) {
        HungrySingle hungrySingle = HungrySingle.getInstance();
        System.out.println(hungrySingle);
    }
}
