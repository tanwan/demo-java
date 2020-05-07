/*
 * Created by LZY on 2016-10-20 20:16.
 */
package com.lzy.demo.base.concurrent.lock;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁的例子
 * 读和读不阻塞,读写和写写都阻塞
 *
 * @author LZY
 * @version v1.0
 */
public class ReadWriteLockTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    // 读锁
    private static Lock readLock = reentrantReadWriteLock.readLock();
    // 写锁
    private static Lock writeLock = reentrantReadWriteLock.writeLock();
    private static int i = 0;

    /**
     * 测试读写锁
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testReentrantReadWriteLock() throws InterruptedException {
        Runnable readRunnable = () -> {
            try {
                readLock.lock();
                Thread.sleep(1000);
                logger.info("i:{}", i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
            }
        };
        Runnable writeRunnable = () -> {
            try {
                writeLock.lock();
                Thread.sleep(1000);
                i++;
                logger.info("i:{}", i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }
        };
        //写写会阻塞
        for (int i = 0; i < 5; i++) {
            new Thread(writeRunnable, "write" + i).start();
        }
        //读读不阻塞
        for (int i = 0; i < 5; i++) {
            new Thread(readRunnable, "read" + i).start();
        }
        Thread.sleep(8000);
    }

}
