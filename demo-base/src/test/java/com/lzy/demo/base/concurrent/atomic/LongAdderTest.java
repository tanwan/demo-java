package com.lzy.demo.base.concurrent.atomic;

import com.lzy.demo.base.ThreadEachCallback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ThreadEachCallback.class)
public class LongAdderTest {
    /**
     * 测试LongAdder,跟AtomicInteger不同的时,LongAdder内部分为多个Cell,这样同等并发下,争夺单个变量更新的操作的线程就会变小,返回值是通过计算所有Cell的累加和
     *
     * @param executorService executorService
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testLongAdder(ExecutorService executorService) throws InterruptedException {
        LongAdder longAdder = new LongAdder();
        IntStream.range(0, 100).forEach(i ->
                executorService.execute(() ->
                        IntStream.range(0, 1000).forEach(j -> longAdder.increment())
                )
        );
        //等待计算结果
        Thread.sleep(100);
        assertEquals(100000, longAdder.longValue());
    }
}
