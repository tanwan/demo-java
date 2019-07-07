/*
 * Created by LZY on 2016-10-24 21:06.
 */
package com.lzy.demo.concurrent.lock;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * CountDownLatch
 *
 * @author LZY
 * @version v1.0
 */
public class CountDownLatchTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    //可以看成有10个任务需要完成
    private static CountDownLatch countDownLatch = new CountDownLatch(10);

    /**
     * 测试CountDownLatch
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testCountDownLatch() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        IntStream.range(0, 10).forEach(i -> executorService.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //每执行完一个任务,就减1
            logger.info("complete:{}", i);
            countDownLatch.countDown();
        }));
        //在这个方法之后的代码,需要在countDownLatch为0的时候才会执行
        countDownLatch.await();
        System.out.println("main run complete");
    }
}
