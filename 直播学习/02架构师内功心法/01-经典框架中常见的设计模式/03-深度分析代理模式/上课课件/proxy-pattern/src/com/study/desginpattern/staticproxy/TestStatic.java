package com.study.desginpattern.staticproxy;

import com.study.desginpattern.staticproxy.entity.Father;
import com.study.desginpattern.staticproxy.entity.Son;
import org.junit.Test;

public class TestStatic {

    @Test
    public void testStatic(){
        Son son = new Son();
        Father father = new Father(son);
        father.findJob();
    }
}
