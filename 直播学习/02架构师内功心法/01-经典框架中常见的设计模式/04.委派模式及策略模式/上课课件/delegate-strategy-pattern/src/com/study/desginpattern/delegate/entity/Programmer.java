package com.study.desginpattern.delegate.entity;

public class Programmer {

    public String doWork() {
        System.out.println("开始工作！！！");
        String work = "完成工作！！！";
        System.out.println("工作完成！！！");
        return work;
    }
}
