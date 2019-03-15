package com.study.desginpattern.abstractfactory;

public class AbstractTest {

    public static void main(String[] args) {
        ACourseFactory courseFactory = new AJavaCourseFactory();
        System.out.println(courseFactory.create().content());
        System.out.println(courseFactory.look().look());
        System.out.println(courseFactory.write().write());

        ACourseFactory pCourseFactory = new APythonCourseFactory();
        System.out.println(pCourseFactory.create().content());
        System.out.println(pCourseFactory.look().look());
        System.out.println(pCourseFactory.write().write());
    }
}
