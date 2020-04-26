/*
 * Created by LZY on 2016-10-20 19:58.
 */
package com.lzy.demo.concurrent.lock;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

/**
 * 信号量例子
 *
 * @author LZY
 * @version v1.0
 */
public class SemaphoreTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    // 阈值为5,允许5个线程同时获取到许可信号
    private static Semaphore semaphore = new Semaphore(5);

    /**
     * 测试信号量
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testSemaphore() throws InterruptedException {
        Runnable runnable = () -> {
            try {
                //尝试获得锁,无法获得则线程等待.使用tryAcquire()没获得则不等待
                semaphore.acquire();
                Thread.sleep(1000);
                logger.info("thread done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //要释放信号量
                semaphore.release();
            }
        };
        IntStream.range(0, 10).forEach(i -> new Thread(runnable, "thread" + i).start());
        Thread.sleep(3000);
    }
}
