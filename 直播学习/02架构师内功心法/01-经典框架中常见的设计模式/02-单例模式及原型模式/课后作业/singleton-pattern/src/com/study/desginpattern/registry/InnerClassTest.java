package com.study.desginpattern.registry;

public class InnerClassTest {
    private Integer a = 11;
    private static Integer b;

    public InnerClassTest(){
    }
    static {
        System.out.println(b);
        System.out.println("InnerClassTest:static:" + System.currentTimeMillis());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Integer getInstance() {
        System.out.println("InnerClassTest:" + System.currentTimeMillis());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Inner.ia;
    }


    public static class Inner{
        public static Integer ia = 1;
        public Inner (){
            System.out.println("Inner:" + System.currentTimeMillis());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        static  {
            ia = 2;
            System.out.println("Inner:static" + System.currentTimeMillis());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
