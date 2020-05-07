/*
 * Created by lzy on 7/17/17.
 */
package com.lzy.demo.base.concurrent.atomic;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

/**
 * AtomicReference的例子
 *
 * @author lzy
 * @version v1.0
 */
public class AtomicReferenceTest {
    private static AtomicReference<String> atomicReference = new AtomicReference<>();

    /**
     * 测试AtomicReference
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testAtomicReference() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
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
        Thread.sleep(1000);
        System.out.println(atomicReference.get());
        executorService.shutdown();
    }
}
