package com.lzy.demo.base.concurrent.base;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

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
        SimpleSynchronized simpleSynchronized = new SimpleSynchronized(sync);
        //这里要保证increase的对象是同一个
        //如果thread1和thread2都使用new Thread(new SynchronizedTest())赋值,那么两个线程需要获得的锁是不一样的,
        //因此不存在竞争关系,也就都能同时访问increase
        Thread thread1 = new Thread(simpleSynchronized);
        Thread thread2 = new Thread(simpleSynchronized);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("i: " + SimpleSynchronized.i);
    }

    private static class SimpleSynchronized implements Runnable {

        private boolean sync = true;

        SimpleSynchronized(boolean sync) {
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
