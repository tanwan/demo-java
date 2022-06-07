package com.lzy.demo.base.concurrent.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

    /**
     * 测试LockSupport
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testLockSupport() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            //阻塞当前线程
            LockSupport.park();
            System.out.println("thread1 run");
        });
        Thread thread2 = new Thread(() -> {
            System.out.println("thread2 run");
            //如果unpark()在park()之前,那么线程运行到park时就会直接返回,而不会阻塞
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //解除阻塞
            LockSupport.unpark(thread1);
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

    }
}
