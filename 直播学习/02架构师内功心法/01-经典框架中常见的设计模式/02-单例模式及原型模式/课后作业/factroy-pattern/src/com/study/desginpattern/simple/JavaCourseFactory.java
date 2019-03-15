package com.study.desginpattern.simple;

import com.study.desginpattern.bean.JavaCourse;

public class JavaCourseFactory {
    public static JavaCourse create(){
        return new JavaCourse();
    }
}
