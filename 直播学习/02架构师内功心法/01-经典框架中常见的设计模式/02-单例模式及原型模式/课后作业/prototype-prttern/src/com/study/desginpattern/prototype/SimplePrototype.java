package com.study.desginpattern.prototype;

import java.util.List;

/**
 * 浅克隆实现原型对象
 */
public class SimplePrototype implements Cloneable{

    private String s1;

    private String s2;

    private String s3;

    private String s4;

    private List<String> stringList;

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    public String getS3() {
        return s3;
    }

    public void setS3(String s3) {
        this.s3 = s3;
    }

    public String getS4() {
        return s4;
    }

    public void setS4(String s4) {
        this.s4 = s4;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
