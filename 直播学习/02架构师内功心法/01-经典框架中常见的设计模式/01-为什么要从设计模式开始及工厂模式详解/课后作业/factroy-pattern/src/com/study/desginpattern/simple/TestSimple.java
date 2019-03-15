package com.study.desginpattern.simple;

import com.study.desginpattern.bean.JavaCourse;
import com.study.desginpattern.bean.PythonCourse;

public class TestSimple {
    public static void main(String[] args) {
        JavaCourse javaCourse = JavaCourseFactory.create();
        System.out.println(javaCourse.content());

        PythonCourse pythonCourse = PythonCourseFactory.create();
        System.out.println(pythonCourse.content());
    }
}
