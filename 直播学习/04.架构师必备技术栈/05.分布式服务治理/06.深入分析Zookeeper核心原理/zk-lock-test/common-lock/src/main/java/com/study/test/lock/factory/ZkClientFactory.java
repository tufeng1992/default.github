package com.study.test.lock.factory;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class ZkClientFactory {

    /**
     * 获取一个zk客户端
     * @return
     * @throws IOException
     */
    public static ZooKeeper getZkClient() throws IOException {
        return new ZooKeeper("127.0.0.1:2181",
                99999999, null);
    }

    /**
     * 释放连接
     * @param zooKeeper
     */
    public static void release(ZooKeeper zooKeeper) {
        if (null != zooKeeper) {
            try {
                zooKeeper.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
