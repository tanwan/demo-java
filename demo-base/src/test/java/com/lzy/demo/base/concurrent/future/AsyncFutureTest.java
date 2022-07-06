package com.lzy.demo.base.concurrent.future;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncFutureTest {

    /**
     * 测试没有async的
     *
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testNoAsync() throws ExecutionException, InterruptedException {
        long startTime = Instant.now().toEpochMilli();
        //使用thenRun()用的还是上一个stage的线程,因此这里用的线程是future的,所以thenRun的两个操作实际上是同步的
        //所以这里的最长用时是1+2+4=7
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        future.thenRun(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future1 spend " + (Instant.now().toEpochMilli() - startTime));
        });
        future.thenRun(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future2 spend " + (Instant.now().toEpochMilli() - startTime));
        });
        Thread.sleep(8000);
    }

    /**
     * 测试有async的
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testAsync() throws InterruptedException {
        long startTime = Instant.now().toEpochMilli();
        //使用thenRunAsync()用的新的线程,所以thenRunAsync的操作都是异步的
        //所以这里的最长用时是1+4=5
        CompletableFuture<Void> asyncFuture = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        asyncFuture.thenRunAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("async1 spend " + (Instant.now().toEpochMilli() - startTime));
        });

        asyncFuture.thenRunAsync(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("async2 spend " + (Instant.now().toEpochMilli() - startTime));
        });
        Thread.sleep(6000);
    }

    /**
     * 测试combine
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void test() throws InterruptedException {
        CompletableFuture<String> combineFuture1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        });
        CompletableFuture<String> combineFuture2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return " world";
        });
        //thenCombine可能会使用combineFuture1的线程或者combineFuture2的线程或者当前线程
        //因此combine函数跟使用到的线程是同步关系的,而thenCombineAsync则都是异步的
        combineFuture1.thenCombine(combineFuture2, (x, y) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return x + y;
        }).thenAccept(System.out::println);
        Thread.sleep(4000);
    }
}
