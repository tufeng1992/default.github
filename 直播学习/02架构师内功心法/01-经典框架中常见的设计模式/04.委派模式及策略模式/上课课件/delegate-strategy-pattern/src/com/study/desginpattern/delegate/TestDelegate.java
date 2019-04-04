package com.study.desginpattern.delegate;

import com.study.desginpattern.delegate.entity.Boss;
import com.study.desginpattern.delegate.entity.Programmer;
import com.study.desginpattern.delegate.entity.ProjectManager;

public class TestDelegate {

    public static void main(String[] args) {
        Boss boss = new Boss();

        Programmer p1 = new Programmer();
        ProjectManager p = new ProjectManager(p1);
        boss.work(p);
    }
}
