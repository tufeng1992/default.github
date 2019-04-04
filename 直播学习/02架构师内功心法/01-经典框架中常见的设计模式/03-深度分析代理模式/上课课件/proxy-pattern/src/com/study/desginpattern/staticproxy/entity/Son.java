package com.study.desginpattern.staticproxy.entity;

public class Son implements Person{
    @Override
    public void findJob() {
        System.out.println("son 找工作！");
    }
}
