package com.lzy.demo.base.concurrent.atomic;

import com.lzy.demo.base.ThreadEachCallback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ThreadEachCallback.class)
public class AtomicReferenceTest {
    /**
     * 测试AtomicReference
     *
     * @param executorService executorService
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testAtomicReference(ExecutorService executorService) throws InterruptedException {
        AtomicReference<String> atomicReference = new AtomicReference<>();
        IntStream.range(0, 100).forEach(i ->
                executorService.execute(() ->
                        IntStream.range(0, 1000).forEach(j -> {
                            String expect;
                            Integer newValue;
                            do {
                                expect = atomicReference.get();
                                newValue = Integer.valueOf(Optional.ofNullable(expect).orElse("0")) + 1;
                                //如果失败则重新获取之前的数据
                            } while (!atomicReference.compareAndSet(expect, newValue.toString()));
                        })
                )
        );
        Thread.sleep(500);
        assertEquals("100000", atomicReference.get());
    }
}
