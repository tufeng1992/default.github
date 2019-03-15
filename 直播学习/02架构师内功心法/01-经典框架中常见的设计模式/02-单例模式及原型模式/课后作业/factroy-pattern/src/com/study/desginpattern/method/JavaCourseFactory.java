package com.study.desginpattern.method;

import com.study.desginpattern.bean.Course;
import com.study.desginpattern.bean.JavaCourse;

public class JavaCourseFactory implements CourseFactory {
    @Override
    public Course create() {
        return new JavaCourse();
    }
}
