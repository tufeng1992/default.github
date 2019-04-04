package com.study.desginpattern.delegate.entity;

public class ProjectManager {

    private Programmer programmerDelegate;

    public ProjectManager(Programmer programmer) {
        programmerDelegate = programmer;
    }

    public String work() {
        System.out.println("开始分派工作！！！");
        String result = programmerDelegate.doWork();
        System.out.println("分派的工作完成了！！！");
        return result;
    }
}
