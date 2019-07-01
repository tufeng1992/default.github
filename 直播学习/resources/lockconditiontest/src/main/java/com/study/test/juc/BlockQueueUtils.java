package com.study.test.juc;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 阻塞队列工具
 * @param <E>
 */
public class BlockQueueUtils<E> {


    /**
     * 队列大小
     */
    private static final int MAX_COUNT = 16;

    /**
     * 计数器
     */
    private AtomicInteger count = new AtomicInteger();

    /**
     * 队列头部节点
     */
    private Node head;

    /**
     * 队列尾部节点
     */
    private Node tail;

    private Lock putLock = new ReentrantLock();

    private Lock takeLock = new ReentrantLock();

    private Condition putCondition = putLock.newCondition();

    private Condition takeCondition = takeLock.newCondition();

    /**
     * 从队列中获取一个对象
     * @return
     */
    public E take() {
        int c = -1;
        try {
            takeLock.lock();
            while (count.get() == 0) {
                System.out.println("take await" + Thread.currentThread().getId());
                takeCondition.await();
            }
            E e = head.element;
            if (null != head.next) {
                head = head.next;
            }
            c = count.getAndDecrement();
            if (c > 1) {
                System.out.println("take signal" + Thread.currentThread().getId());
                takeCondition.signal();
            }
            return e;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            takeLock.unlock();
        }
        try {
            putLock.lock();
            if (c == MAX_COUNT) {
                putCondition.signal();
            }
        } finally {
            putLock.unlock();
        }
        return null;
    }

    /**
     * 塞入一个对象到队列
     * @param element
     */
    public void put(E element) {
        int c = -1;
        try {
            putLock.lock();
            //如果队列容量已经达到最大容量则阻塞当前线程
            while (count.get() == MAX_COUNT) {
                System.out.println("put await" + Thread.currentThread().getId());
                putCondition.await();
            }
            Node n = new Node(element);
            //如果head为空代表tail也为空，则先初始化head和tail。如果head不为空则直接从tail塞入节点，并将tail指向到新塞入的节点
            if (null == head) {
                head = n;
                tail = head;
            } else {
                tail.next = n;
                tail = n;
            }
            c = count.getAndIncrement();
            if (++c < MAX_COUNT) {
                System.out.println("put signal" + Thread.currentThread().getId());
                putCondition.signal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            putLock.unlock();
        }
        try {
            takeLock.lock();
            if (c > 0) {
                takeCondition.signal();
            }
        } finally {
            takeLock.unlock();
        }
    }

    private class Node {
        private E element;

        private Node next;

        public Node(E element) {
            this.element = element;
        }
    }
}
