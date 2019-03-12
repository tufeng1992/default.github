package com.study.desginpattern.lazy;

import org.junit.Test;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;

public class LazyTest {

    /**
     * 普通懒汉式测试
     * @throws InterruptedException
     */
    @Test
    public void lazyTest1() throws InterruptedException {
        int count = 100;
        final CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            new Thread(
                    () -> {
                        LazySingleton lazySingleton = LazySingleton.getInstance();
                        System.out.println(lazySingleton);
                        countDownLatch.countDown();
                    }
            ).start();
        }
        countDownLatch.await();
        System.out.println("end...");
    }

    /**
     * 线程安全懒汉式测试
     * @throws InterruptedException
     */
    @Test
    public void lazyTest2() throws InterruptedException {
        int count = 1000;
        final CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            new Thread(
                    () -> {
                        LazySingletonDoubleCheck lazySingletonDoubleCheck = LazySingletonDoubleCheck.getInstance();
                        System.out.println(lazySingletonDoubleCheck);
                        countDownLatch.countDown();
                    }
            ).start();
        }
        countDownLatch.await();
        System.out.println("end...");
    }

    /**
     * 防序列化破坏懒汉式测试
     */
    @Test
    public void lazyTest3() throws IOException, ClassNotFoundException {
        String fileNameSafe = "LazySingletonSerialize";
        String fileName = "LazySingletonDoubleCheck";
        LazySingletonDoubleCheck lazySingletonDoubleCheck = LazySingletonDoubleCheck.getInstance();
        ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream(fileName));
        out1.writeObject(lazySingletonDoubleCheck);
        out1.close();
        System.out.println(fileName + ":" + lazySingletonDoubleCheck);
        ObjectInputStream in1 = new ObjectInputStream(new FileInputStream(fileName));
        System.out.println(fileName + ":" + in1.readObject());
        in1.close();

        LazySingletonSerialize lazySingletonSerialize = LazySingletonSerialize.getInstance();
        ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(fileNameSafe));
        out2.writeObject(lazySingletonSerialize);
        out2.close();
        System.out.println(fileNameSafe + ":" + lazySingletonSerialize);
        ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(fileNameSafe));
        System.out.println(fileNameSafe + ":" + in2.readObject());
        in2.close();
    }

    /**
     * 测试simple懒汉式单例
     */
    @Test
    public void testLazy4() throws InterruptedException {
        int count = 1000;
        final CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            new Thread(
                    () -> {
                        SimpleLazySingleton simpleLazySingleton = SimpleLazySingleton.getInstance();
                        System.out.println(simpleLazySingleton);
                        countDownLatch.countDown();
                    }
            ).start();
        }
        countDownLatch.await();
        System.out.println("end...");
    }

    @Test
    public void Test5() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        LazySingletonReflect lazySingletonReflect = LazySingletonReflect.getInstance();
//
//        Constructor constructor = LazySingletonReflect.class.getDeclaredConstructor();
//        constructor.setAccessible(true);
//        Object obj = constructor.newInstance();
//        System.out.println(lazySingletonReflect == obj);


        SimpleLazySingleton simpleLazySingleton = SimpleLazySingleton.getInstance();
        Constructor constructor2 = SimpleLazySingleton.class.getDeclaredConstructor();
        constructor2.setAccessible(true);
        Object obj2 = constructor2.newInstance();
        System.out.println(simpleLazySingleton == obj2);
    }
}
