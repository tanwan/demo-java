/*
 * Created by LZY on 7/3/2017 21:48.
 */


package com.lzy.demo.base.future;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * 测试Future
 *
 * @author LZY
 * @version v1.0
 */
public class FutureTest {

    /**
     * 测试future
     *
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testFuture() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<String> callback = () -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "hello world";
        };
        //ExecutorService#submit可以返回一个future
        Future<String> future = executor.submit(callback);
        System.out.println(future.get());
        FutureTask<String> futureTask = new FutureTask(callback);
        //FutureTask可以提交给ExecutorService执行
        executor.submit(futureTask);
        System.out.println(futureTask.get());
    }

    /**
     * 测试CompletableFuture#runAsync()
     */
    @Test
    public void testRunAsync() {
        CompletableFuture.runAsync(() ->
                //异步执行
                System.out.println("runAsync")
        ).whenComplete((v, e) -> System.out.println("complete"));
    }

    /**
     * 测试测试CompletableFuture#SupplyAsync
     */
    @Test
    public void testSupplyAsync() {
        CompletableFuture.supplyAsync(() -> {
            return "dd";
            //throw new RuntimeException("exception");
        }).whenComplete((value, e) -> {
            System.out.println("complete");
            System.out.println(value);
            //e.printStackTrace();
            //如果没有异常的话,这个e就会为空
            System.out.println(e);
        }).thenAccept(System.out::println);
    }


    /**
     * 测试手动完成
     *
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testComplete() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture();
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            completableFuture.complete("success");
        }).start();
        //这边会一直阻塞,直到其它线程调用到CompletableFuture#complete()
        System.out.println(completableFuture.get());
    }

}
