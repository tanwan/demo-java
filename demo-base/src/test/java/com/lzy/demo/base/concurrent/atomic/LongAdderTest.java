/*
 * Created by lzy on 7/12/17.
 */
package com.lzy.demo.base.concurrent.atomic;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

/**
 * longAdder
 *
 * @author lzy
 * @version v1.0
 * @see LongAdder
 */
public class LongAdderTest {
    private static LongAdder longAdder = new LongAdder();

    /**
     * 测试LongAdder,跟AtomicInteger不同的时,LongAdder内部分为多个Cell,这样同等并发下,争夺单个变量更新的操作的线程就会变小,返回值是通过计算所有Cell的累加和
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testLongAdder() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        IntStream.range(0, 100).forEach(i ->
                executorService.execute(() ->
                        IntStream.range(0, 1000).forEach(j -> longAdder.increment())
                )
        );
        //等待计算结果
        Thread.sleep(1000);
        System.out.println(longAdder.longValue());
        executorService.shutdown();
    }
}
