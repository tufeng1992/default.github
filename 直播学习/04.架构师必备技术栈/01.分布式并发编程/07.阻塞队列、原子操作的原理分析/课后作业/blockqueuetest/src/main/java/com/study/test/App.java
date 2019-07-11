package com.study.test;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Hello world!
 *
 */
public class App 
{
    private static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(16);

    public static void main( String[] args) throws InterruptedException {
        App a = new App();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    a.park("carNo" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        Thread.sleep(2000);
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    a.out();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();
        Thread.sleep(5000);
    }

    /**
     * 停车
     * @param carNo
     */
    private static void park( String carNo) throws InterruptedException {
        System.out.println("车牌号：" + carNo + " 进入停车场");
        queue.put(carNo);
    }

    private static String out() throws InterruptedException {
        String carNo = queue.take();
        System.out.println("车牌号：" + carNo + " 离开停车场");
        return carNo;
    }
}
