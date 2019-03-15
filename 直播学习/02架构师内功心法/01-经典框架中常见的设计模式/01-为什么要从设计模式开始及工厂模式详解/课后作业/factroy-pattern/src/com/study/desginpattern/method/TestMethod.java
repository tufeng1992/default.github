package com.study.desginpattern.method;


import com.study.desginpattern.bean.Course;

public class TestMethod {

    public static void main(String[] args) {
        CourseFactory courseFactory = new JavaCourseFactory();
        Course javaCourse = courseFactory.create();
        System.out.println(javaCourse.content());
        CourseFactory pythonCourseFactory = new PythonCourseFactory();
        Course pythonCourse = pythonCourseFactory.create();
        System.out.println(pythonCourse.content());
    }
}
