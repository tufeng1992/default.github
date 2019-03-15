package com.study.desginpattern.abstractfactory;

import com.study.desginpattern.bean.*;

public class AJavaCourseFactory implements ACourseFactory {
    @Override
    public Course create() {
        return new JavaCourse();
    }

    @Override
    public Video look() {
        return new JavaVideo();
    }

    @Override
    public Note write() {
        return new JavaNote();
    }
}
