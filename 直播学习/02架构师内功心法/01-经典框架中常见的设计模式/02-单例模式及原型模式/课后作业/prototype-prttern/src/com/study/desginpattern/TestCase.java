package com.study.desginpattern;

import com.study.desginpattern.prototype.DeepPrototype;
import com.study.desginpattern.prototype.SimplePrototype;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestCase {

    @Test
    public void test1() throws CloneNotSupportedException {
        SimplePrototype simplePrototype = new SimplePrototype();
        simplePrototype.setS1("s1");
        simplePrototype.setS2("s2");
        simplePrototype.setS3("s3");
        simplePrototype.setS4("s4");
        List<String> stringList = new ArrayList<String>();
        stringList.add("l1");
        stringList.add("l2");
        simplePrototype.setStringList(stringList);
        System.out.println(simplePrototype);
        System.out.println(simplePrototype.getS2());
        System.out.println(simplePrototype.getStringList());
        SimplePrototype simplePrototypeClone = (SimplePrototype) simplePrototype.clone();
        System.out.println(simplePrototypeClone);
        System.out.println(simplePrototypeClone.getS2());
        simplePrototype.setS2("s5");
        stringList.add("l3");
        System.out.println(simplePrototypeClone.getS2());
        System.out.println(simplePrototypeClone.getStringList());
    }

    @Test
    public void test2() throws CloneNotSupportedException {
        DeepPrototype deepPrototype = new DeepPrototype();
        deepPrototype.setS1("s1");
        deepPrototype.setS2("s2");
        deepPrototype.setS3("s3");
        deepPrototype.setS4("s4");
        List<String> stringList = new ArrayList<String>();
        stringList.add("l1");
        stringList.add("l2");
        deepPrototype.setStringList(stringList);
        System.out.println(deepPrototype);
        System.out.println(deepPrototype.getS2());
        System.out.println(deepPrototype.getStringList());
        DeepPrototype deepPrototypeClone = (DeepPrototype) deepPrototype.clone();
        System.out.println(deepPrototypeClone);
        System.out.println(deepPrototypeClone.getS2());
        deepPrototype.setS2("s5");
        stringList.add("l3");
        System.out.println(deepPrototypeClone.getS2());
        System.out.println(deepPrototypeClone.getStringList());
        System.out.println(deepPrototype.getStringList());
    }
}
