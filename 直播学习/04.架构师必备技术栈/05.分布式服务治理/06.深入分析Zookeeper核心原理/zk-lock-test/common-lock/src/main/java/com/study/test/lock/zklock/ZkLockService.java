package com.study.test.lock.zklock;

import com.study.test.lock.base.Lock;
import com.study.test.lock.constants.Const;
import com.study.test.lock.factory.ZkClientFactory;
import lombok.Data;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ZkLockService implements Lock {

    private static Map<Thread, LockData> threadMap = new ConcurrentHashMap<>();

    @Override
    public void lock(String key) {
        try {
            ZooKeeper zk = ZkClientFactory.getZkClient();
            Thread thread = Thread.currentThread();
            LockData lockData = putLockData(key, thread);
            //非1情况为重入锁
            if (lockData.getCount().get() == 1) {
                while (true) {
                    synchronized (this) {
                        if (zk.exists(Const.ZK_LOCK_PATH + key, watchedEvent -> {
                            if (Watcher.Event.EventType.NodeDeleted == watchedEvent.getType()) {
                                synchronized (this) {
                                    this.notify();
                                }
                            }
                        }) == null) {
                            System.out.println("============================================================ThreadId :" + Thread.currentThread().getId() + " : lock" );
                            zk.create(Const.ZK_LOCK_PATH + key, key.getBytes(),
                                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                            ZkClientFactory.release(zk);
                            break;
                        }
                        this.wait();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean tryLock(String key, long waitMs, TimeUnit timeUnit) {
        try {
            Thread thread = Thread.currentThread();
            LockData lockData = putLockData(key, thread);
            //非1情况为重入锁
            if (lockData.getCount().get() == 1) {
                long startMillis = System.currentTimeMillis();
                ZooKeeper zk = ZkClientFactory.getZkClient();
                while (true) {
                    synchronized (this) {
                        if (zk.exists(Const.ZK_LOCK_PATH + key, watchedEvent -> {
                            if (Watcher.Event.EventType.NodeDeleted == watchedEvent.getType()) {
                                synchronized (this) {
                                    this.notify();
                                }
                            }
                        }) == null) {
                            System.out.println("============================================================ThreadId :" + Thread.currentThread().getId() + " : lock" );
                            zk.create(Const.ZK_LOCK_PATH + key, key.getBytes(),
                                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                            ZkClientFactory.release(zk);
                            return true;
                        }
                        //等待超时后未获取到锁则返回失败
                        if ((System.currentTimeMillis() - startMillis) < waitMs) {
                            this.wait(timeUnit.toMillis(waitMs));
                        } else {
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void release(String key) {
        try {
            Thread thread = Thread.currentThread();
            LockData l = getLockData(thread);
            if (null == l) {
                return;
            }
            if (l.getCount().get() > 1) {
                l.getCount().decrementAndGet();
                return;
            }
            if (l.getCount().get() == 1) {
                ZooKeeper zk = ZkClientFactory.getZkClient();
                synchronized (this) {
                    Stat stat = zk.exists(Const.ZK_LOCK_PATH + key, true);
                    if (stat != null) {
                        System.out.println("============================================================ThreadId :" + Thread.currentThread().getId() + " : release" );
                        zk.delete(Const.ZK_LOCK_PATH + key, stat.getVersion());
                        ZkClientFactory.release(zk);
                    }
                    //锁释放后移除缓存
                    threadMap.remove(thread);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }


    private LockData getLockData(Thread t) {
        return threadMap.get(t);
    }

    private LockData putLockData(String key, Thread t) {
        LockData lockData = getLockData(t);
        if (null == lockData) {
            lockData = new LockData();
            lockData.setPath(key);
            lockData.setThread(t);
            lockData.setCount(new AtomicInteger(1));
            threadMap.put(t, lockData);
            return lockData;
        }
        lockData.getCount().incrementAndGet();
        return lockData;
    }

    @Data
    private class LockData {
        private String path;

        private Thread thread;

        private AtomicInteger count;
    }
}
