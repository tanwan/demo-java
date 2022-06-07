package com.lzy.demo.base.concurrent.lock;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition和ReentrantLock配合的例子
 *
 * @author LZY
 * @version v1.0
 */
public class ReentrantLockConditionTest {

    /**
     * 测试条件
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testCondition() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        // 队列不是空的条件
        Condition notEmpty = reentrantLock.newCondition();
        // 队列不是满的条件
        Condition notFull = reentrantLock.newCondition();
        List<Integer> list = new ArrayList<>();
        //生产者
        Runnable producer = () -> {
            while (true) {
                try {
                    //模拟生产数据的时间
                    Thread.sleep(500);
                    reentrantLock.lock();
                    while (list.size() >= 5) {
                        System.out.println("list full");
                        //释放锁,线程进入等待状态,这个可以理解成 队列已满,等待队列不是满的条件
                        notFull.await();
                    }
                    list.add(1);
                    System.out.println("product 1");
                    //发出通知,队列不是空的条件成立,只唤醒notEmpty.await()等待的线程
                    notEmpty.signalAll();
                } catch (InterruptedException ignored) {
                } finally {
                    reentrantLock.unlock();
                }
            }
        };
        Runnable consumer = () -> {
            while (true) {
                try {
                    reentrantLock.lock();
                    while (list.isEmpty()) {
                        System.out.println("list empty");
                        // 释放锁,线程进入等待状态,这个可以理解成 队列已空,等待队列不是空的条件
                        notEmpty.await();
                    }
                    System.out.println("consumer 1");
                    list.remove(0);
                    // 发出通知,队列不是满的条件成立,只唤醒notFull.await()等待的线程
                    notFull.signalAll();
                } catch (InterruptedException ignored) {
                } finally {
                    reentrantLock.unlock();
                    try {
                        //模拟处理数据的时间
                        Thread.sleep(2000);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        };
        Thread producerThread = new Thread(producer);
        producerThread.start();
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
        Thread.sleep(5000);
        producerThread.interrupt();
        consumerThread.interrupt();
    }
}
