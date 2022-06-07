package com.lzy.demo.base.concurrent.atomic;


import com.lzy.demo.base.ThreadEachCallback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * cas的Integer的例子
 *
 * @author LZY
 * @version v1.0
 * @see AtomicInteger
 * @see java.util.concurrent.atomic.AtomicLong
 * @see java.util.concurrent.atomic.AtomicReference
 */
@ExtendWith(ThreadEachCallback.class)
public class AtomicIntegerTest {

    /**
     * 测试AtomicInteger
     *
     * @param executorService executorService
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testAtomicInteger(ExecutorService executorService) throws InterruptedException {
        //线程安全的Integer
        AtomicInteger atomicInteger = new AtomicInteger();
        IntStream.range(0, 100).forEach(i ->
                executorService.execute(() ->
                        IntStream.range(0, 1000).forEach(j -> atomicInteger.incrementAndGet())
                )
        );
        //等待计算结果
        Thread.sleep(100);
        assertEquals(100000, atomicInteger.get());
    }
}
