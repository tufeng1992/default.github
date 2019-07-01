package com.study.test;

import com.study.test.juc.BlockQueueUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException {
        BlockQueueUtils<Object> lqu = new BlockQueueUtils<>();
        for (int i = 0; i < 3; i++) {
            Thread t2 = new Thread(() -> {
                System.out.println(lqu.take());
            });
            t2.start();
        }

        Thread.sleep(2000);
        for (int i = 0; i < 20; i++) {
            TestClass t = new TestClass(i, "str" + i);
            Thread t1 = new Thread(() -> {
                lqu.put(t);
            });
            t1.start();
        }
        Thread.sleep(2000);
        System.out.println(lqu.take());
//        TestClass t = new TestClass(20, "str" + 20);
//        lqu.put(t);
        Thread.sleep(1000);
    }
}

class TestClass {
    private Integer id;

    private String str;

    public TestClass(Integer id, String str) {
        this.id = id;
        this.str = str;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "id=" + id +
                ", str='" + str + '\'' +
                '}';
    }
}
