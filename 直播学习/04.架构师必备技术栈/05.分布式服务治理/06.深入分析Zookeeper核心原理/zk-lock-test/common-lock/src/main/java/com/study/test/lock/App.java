package com.study.test.lock;

import com.study.test.lock.base.Lock;
import com.study.test.lock.zklock.ZkLockService;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        Lock lock = new ZkLockService();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                String key = "testLock";
                System.out.println("======================hreadId :" + Thread.currentThread().getId() + " : start lock" );
                lock.tryLock(key, 1000, TimeUnit.MILLISECONDS);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("======================ThreadId :" + Thread.currentThread().getId() + " : start release" );
                lock.release(key);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
    }
}
