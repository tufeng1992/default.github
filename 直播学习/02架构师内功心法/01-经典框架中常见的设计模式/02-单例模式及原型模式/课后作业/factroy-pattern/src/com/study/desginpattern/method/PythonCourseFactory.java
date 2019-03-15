package com.study.desginpattern.method;

import com.study.desginpattern.bean.Course;
import com.study.desginpattern.bean.PythonCourse;

public class PythonCourseFactory implements CourseFactory{
    @Override
    public Course create() {
        return new PythonCourse();
    }
}
