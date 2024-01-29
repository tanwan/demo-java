package com.lzy.demo.base.feature.java21;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class VirtualThreadTest {

    /**
     * 使用Thread.ofVirtual创建虚拟线程
     * 虚拟线程实际上是在一个守护线程(CarrierThread)中执行的
     */
    @Test
    public void testVirtualThread() {
        // 使用ofVirtual创建虚拟线程, start表示立即执行
        Thread.ofVirtual().start(() -> {
                    // 未命名的虚拟线程的名称为空
                    System.out.println(LocalTime.now() + " unnamed start " + Thread.currentThread().getName());
                    sleep(1000);
                    System.out.println(LocalTime.now() + " unnamed end");
                }
        );
        // 虚拟线程不会阻塞主线程
        System.out.println(LocalTime.now() + " main " + Thread.currentThread().getName());

        // name可以为虚拟线程命名, unstarted表示不立即执行, 需要显式调用start
        Thread thread = Thread.ofVirtual().name("named-thread").unstarted(() -> {
                    System.out.println(LocalTime.now() + " unstarted start " + Thread.currentThread().getName());
                    sleep(1000);
                    System.out.println(LocalTime.now() + " unstarted end");
                }
        );
        sleep(500);
        // unstarted没有调用start就不会执行
        thread.start();


        // 使用Thread.startVirtualThread启动虚拟线程
        Thread.startVirtualThread(() -> {
            System.out.println(LocalTime.now() + " thread start " + Thread.currentThread().getName());
            sleep(1000);
            System.out.println(LocalTime.now() + " thread end");
        });
        sleep(1200);
    }


    /**
     * 使用Executors创建虚拟线程
     */
    @Test
    public void testExecutors() {
        // 使用Executors.newVirtualThreadPerTaskExecutor()创建虚拟线程
        long start = System.currentTimeMillis();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10).forEach(i -> executor.submit(() -> {
                // Executors创建的虚拟线程的名称为空
                System.out.println(LocalTime.now() + " start " + Thread.currentThread().getName());
                sleep(1000);
                System.out.println(LocalTime.now() + " end " + Thread.currentThread().getName());
                return i;
            }));
        }
        System.out.println("spend " + (System.currentTimeMillis() - start));
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
