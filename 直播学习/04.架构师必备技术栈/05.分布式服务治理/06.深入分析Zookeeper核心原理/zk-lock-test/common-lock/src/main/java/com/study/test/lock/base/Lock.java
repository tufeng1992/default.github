package com.study.test.lock.base;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 */
public interface Lock {

    /**
     * 获得锁
     * @param key
     */
    void lock(String key);

    /**
     * 尝试获得锁
     * @param key
     * @param waitMs
     * @param timeUnit
     * @return
     */
    boolean tryLock(String key, long waitMs, TimeUnit timeUnit);

    /**
     * 释放锁
     * @param key
     */
    void release(String key);
}
