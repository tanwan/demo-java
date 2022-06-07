package com.lzy.demo.base.concurrent.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ReentrantLockTest {
    private static ReentrantLock reentrantLock = new ReentrantLock();

    private static int i = 0;

    /**
     * 测试重入锁
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testReentrantLock() throws InterruptedException {
        Runnable runnable = () -> IntStream.range(0, 10000).forEach(j -> {
            //一个线程允许调用多次ReentrantLock#lock()(重入)但是要跟ReentrantLock#unlock()一一对应,也就是
            //有多少个lock(),就要有多少个unlock(),少了不会释放资源,多了会出异常
            reentrantLock.lock();
            try {
                i++;
            } finally {
                //需要手动释放
                reentrantLock.unlock();
            }
        });
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("i:" + i);
    }
}
