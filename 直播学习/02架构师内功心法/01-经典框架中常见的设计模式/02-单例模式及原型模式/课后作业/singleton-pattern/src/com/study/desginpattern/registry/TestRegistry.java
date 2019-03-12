package com.study.desginpattern.registry;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * 测试注册式单例模式
 */
public class TestRegistry {


    @Test
    public void TestRegisry1 () {
        Object obj = EnumSimple.KEY1;
        System.out.println(obj);
        Object obj2 = EnumSimple.KEY1;
        System.out.println(obj == obj2);
    }

    @Test
    public void TestRegisry2 () throws InterruptedException {
        RegistrySingleton registrySingleton = RegistrySingleton.getInstance();
        String testBean = "testBean";
        registrySingleton.setBean(testBean, ExampleSingleton.class);

        int count = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                System.out.println(registrySingleton.getBean(testBean));
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
    }

    @Test
    public void test3(){
        InnerClassTest innerClassTest = new InnerClassTest();
        System.out.println(innerClassTest);
        System.out.println(InnerClassTest.Inner.ia);
    }
}
