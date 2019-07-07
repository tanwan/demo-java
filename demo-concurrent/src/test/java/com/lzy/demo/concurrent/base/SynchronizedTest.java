/*
 * Created by LZY on 2016-10-18 20:37.
 */
package com.lzy.demo.concurrent.base;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

/**
 * synchronized的例子
 *
 * @author LZY
 * @version v1.0
 */
public class SynchronizedTest {


    /**
     * 测试没有synchronized
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testNoSynchronized() throws InterruptedException {
        testSynchronized(false);
    }

    /**
     * 测试synchronized
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testSynchronized() throws InterruptedException {
        testSynchronized(true);
    }


    private void testSynchronized(boolean sync) throws InterruptedException {
        SynchronizedSample synchronizedSample = new SynchronizedSample(sync);
        //这里要保证increase的对象是同一个
        //如果thread1和thread2都使用new Thread(new SynchronizedTest())赋值,那么两个线程需要获得的锁是不一样的,
        //因此不存在竞争关系,也就都能同时访问increase
        Thread thread1 = new Thread(synchronizedSample);
        Thread thread2 = new Thread(synchronizedSample);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("i: " + SynchronizedSample.i);
    }

    private static class SynchronizedSample implements Runnable {

        private boolean sync = true;

        public SynchronizedSample(boolean sync) {
            this.sync = sync;
        }

        private static int i = 0;

        /**
         * increase
         * 线程要运行这个方法，必须获取这个方法所在对象的锁
         * 如果这个方法是static的话,那么必须获得这个方法所在类的锁
         */
        private synchronized void increase() {
            i++;
        }

        private void noSyncIncrease() {
            i++;
        }

        @Override
        public void run() {
            IntStream.range(0, 10000).forEach(i -> {
                if (sync) {
                    increase();
                } else {
                    noSyncIncrease();
                }
            });
        }
    }
}
