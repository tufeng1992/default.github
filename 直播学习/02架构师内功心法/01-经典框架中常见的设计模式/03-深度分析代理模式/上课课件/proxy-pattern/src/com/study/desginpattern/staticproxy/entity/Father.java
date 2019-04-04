package com.study.desginpattern.staticproxy.entity;

public class Father implements Person {

    private Son son;

    public Father(Son son) {
        this.son = son;
    }


    @Override
    public void findJob() {
        System.out.println("帮忙找工作");
        son.findJob();
        System.out.println("找好了！");
    }
}
