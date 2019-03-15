package com.study.desginpattern.abstractfactory;

import com.study.desginpattern.bean.Course;
import com.study.desginpattern.bean.Note;
import com.study.desginpattern.bean.Video;

public interface ACourseFactory {

    Course create();

    Video look();

    Note write();
}
