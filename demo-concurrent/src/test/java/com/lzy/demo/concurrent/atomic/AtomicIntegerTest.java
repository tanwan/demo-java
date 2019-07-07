/*
 * Created by LZY on 2016-10-26 22:34.
 */
package com.lzy.demo.concurrent.atomic;


import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * cas的Integer的例子
 *
 * @author LZY
 * @version v1.0
 * @see AtomicInteger
 * @see java.util.concurrent.atomic.AtomicLong
 * @see java.util.concurrent.atomic.AtomicReference
 */
public class AtomicIntegerTest {
    //线程安全的Integer
    private static AtomicInteger atomicInteger = new AtomicInteger();

    /**
     * 测试AtomicInteger
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testAtomicInteger() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        IntStream.range(0, 100).forEach(i ->
                executorService.execute(() ->
                        IntStream.range(0, 1000).forEach(j -> atomicInteger.incrementAndGet())
                )
        );
        //等待计算结果
        Thread.sleep(1000);
        System.out.println(atomicInteger.get());
        executorService.shutdown();
    }
}
