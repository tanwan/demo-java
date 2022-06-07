package com.lzy.demo.base.concurrent.threadpool;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadPoolTest {

    /**
     * 测试单线程池
     */
    @Test
    public void testSingleThreadExecutor() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        IntStream.range(0, 10).forEach(i -> executorService.execute(() -> System.out.println(i)));
        executorService.shutdown();
    }

    /**
     * 测试schedule
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testSchedule() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        System.out.println("start: " + LocalDateTime.now());
        // 延迟执行
        scheduledExecutorService.schedule(() -> System.out.println("schedule: " + LocalDateTime.now()), 2, TimeUnit.SECONDS);
        Thread.sleep(3000);
        scheduledExecutorService.shutdown();
    }

    /**
     * 测试scheduleAtFixedRate
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testscheduleAtFixedRate() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        System.out.println("start: " + LocalDateTime.now());
        // 固定频率执行,period为下一个任务开始时间 减去 上一个任务开始时间
        scheduledExecutorService.scheduleAtFixedRate(() -> System.out.println("schedule: " + LocalDateTime.now()), 1, 2, TimeUnit.SECONDS);
        Thread.sleep(10000);
        scheduledExecutorService.shutdown();
    }

    /**
     * 测试scheduleWithFixedDelay
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testScheduleWithFixedDelay() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        System.out.println("start: " + LocalDateTime.now());
        // 固定间隔执行,period为下一个任务开始时间 减去 上一个任务结束时间
        scheduledExecutorService.scheduleWithFixedDelay(() -> System.out.println("schedule: " + LocalDateTime.now()), 1, 2, TimeUnit.SECONDS);
        Thread.sleep(10000);
        scheduledExecutorService.shutdown();
    }
}
