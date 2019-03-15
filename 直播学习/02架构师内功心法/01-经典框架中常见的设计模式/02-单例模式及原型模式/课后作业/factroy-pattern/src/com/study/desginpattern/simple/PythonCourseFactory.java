package com.study.desginpattern.simple;

import com.study.desginpattern.bean.PythonCourse;

public class PythonCourseFactory {

    public static PythonCourse create(){
        return new PythonCourse();
    }
}
