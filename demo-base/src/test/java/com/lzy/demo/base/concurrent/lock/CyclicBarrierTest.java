/*
 * Created by LZY on 2016-10-24 21:34.
 */
package com.lzy.demo.base.concurrent.lock;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier
 *
 * @author LZY
 * @version v1.0
 */
public class CyclicBarrierTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 测试CyclicBarrier
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testCyclicBarrier() throws InterruptedException {
        //只有计数器达到指定的值,才会唤醒阻塞的线程,并触发,并且计数器置0
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> {
            System.out.println("cyclicBarrier reset");
        });
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    // 前面9个线程都被阻塞了,然后等第10个线程执行的时候,全部10线程就一次执行了
                    cyclicBarrier.await();
                    logger.info("first run");
                    // 然后又重新调用await方法,又需要等10个
                    cyclicBarrier.await();
                    logger.info("second run");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "thread" + i).start();
            Thread.sleep(300);
            System.out.println("thread" + i + " start");
        }
        Thread.sleep(5000);
    }
}
