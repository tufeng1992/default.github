package com.study.desginpattern.delegate.entity;

public class Boss {

    public void work(ProjectManager projectManager) {
        System.out.println(projectManager.work());
    }
}
