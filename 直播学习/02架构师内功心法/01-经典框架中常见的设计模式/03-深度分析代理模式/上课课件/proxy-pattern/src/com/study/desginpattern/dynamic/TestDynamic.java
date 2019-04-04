package com.study.desginpattern.dynamic;

import com.study.desginpattern.dynamic.entity.AMan;
import com.study.desginpattern.dynamic.entity.Person;
import com.study.desginpattern.dynamic.entity.TMeipo;

public class TestDynamic {

    /**
     * 手写jdk动态代理需要以下几个步骤
     * 1.代理执行InvocationHandler，实现invoke函数。
     * 2.通过Proxy类newInstance，实例化需要代理的对象。
     * 3.媒婆实现InvocationHandler，并通过调用Proxy类实例化代理对象，然后通过invoke执行。
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Person person = (Person) new TMeipo().getInstance(new AMan());
        System.out.println(person.getClass());
        person.findObject();
    }
}
