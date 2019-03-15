package com.study.desginpattern.abstractfactory;

import com.study.desginpattern.bean.*;

public class APythonCourseFactory implements ACourseFactory {
    @Override
    public Course create() {
        return new PythonCourse();
    }

    @Override
    public Video look() {
        return new PythonVideo();
    }

    @Override
    public Note write() {
        return new PythonNote();
    }
}
