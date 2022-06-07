package com.lzy.demo.spring.scheduled;

import org.junit.jupiter.api.Test;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@EnableScheduling
public class ScheduledTest {

    @SpringJUnitConfig(classes = {SimpleScheduledCron.class})
    public static class CronTest extends ScheduledTest {
        /**
         * 测试使用cron
         * 执行时间大于调用频率,执行的时候,发现上一次的任务还未结束,则此次任务不执行,等待下一次执行
         *
         * @throws InterruptedException the interrupted exception
         */
        @Test
        public void testCron() throws InterruptedException {
            Thread.sleep(5000);
        }
    }

    @SpringJUnitConfig(classes = SimpleScheduledDelay.class)
    public static class DelayTest extends ScheduledTest {
        /**
         * 上个任务执行完后才执行下一个任务
         *
         * @throws InterruptedException the interrupted exception
         */
        @Test
        public void testDelay() throws InterruptedException {
            Thread.sleep(10000);
        }
    }

    @SpringJUnitConfig(classes = SimpleScheduledRate.class)
    public static class RateTest extends ScheduledTest {
        /**
         * 固定周期执行任务,执行的时候,发现上一次的任务还未结束,则此次任务不执行,等待下一次执行
         *
         * @throws InterruptedException the interrupted exception
         */
        @Test
        public void testRate() throws InterruptedException {
            Thread.sleep(10000);
        }
    }

    @SpringJUnitConfig(classes = SimpleSchedulingConfigurer.class)
    public static class SchedulingConfigurerTest extends ScheduledTest {
        /**
         * 使用SchedulingConfigurer创建定时任务
         *
         * @throws InterruptedException the interrupted exception
         */
        @Test
        public void testSchedulingConfigurer() throws InterruptedException {
            Thread.sleep(5000);
        }
    }

    @SpringJUnitConfig(classes = {SimpleScheduledCron.class, SimpleScheduledRate.class, ScheduledConfig.class})
    public static class ConcurrentTest extends ScheduledTest {
        /**
         * 测试并发执行,同一时间可以并发执行不同的任务
         *
         * @throws InterruptedException the interrupted exception
         */
        @Test
        public void testConcurrent() throws InterruptedException {
            Thread.sleep(10000);
        }
    }

}
