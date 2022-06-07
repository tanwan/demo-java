package com.lzy.demo.base.concurrent.queue;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class QueueTest {

    /**
     * 测试非阻塞队列
     * @throws InterruptedException Interrupted Exception
     */
    @Test
    public void testConcurrentLinkedQueue() throws InterruptedException {
        ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        //相同线程的就是严格的FIFO,不同线程其实也是FIFO,只是线程执行有先后顺序,导致元素入队有先后
        new Thread(() -> IntStream.range(0, 5).forEach(concurrentLinkedQueue::add)).start();
        new Thread(() -> IntStream.range(5, 10).forEach(concurrentLinkedQueue::add)).start();

        //确保已经入队
        Thread.sleep(500L);
        new Thread(() -> {
            //不能使用size(),否则会遍历整个队列
            while (!concurrentLinkedQueue.isEmpty()) {
                System.out.println(concurrentLinkedQueue.poll());
            }
        }).start();
    }


    /**
     * 测试阻塞队列出队
     * @throws Exception exception
     */
    @Test
    public void testBlockingQueueOut() throws Exception {
        //基于数组
        ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(3);
        //基于链表
        LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>(3);

        //使用remove,空队列,获取元素报异常
        Assertions.assertThatCode(() -> arrayBlockingQueue.remove()).isInstanceOf(NoSuchElementException.class);
        Assertions.assertThatCode(() -> linkedBlockingQueue.remove()).isInstanceOf(NoSuchElementException.class);

        //使用poll,空队列,获取元素为null
        Assertions.assertThat(arrayBlockingQueue.poll()).isNull();
        Assertions.assertThat(linkedBlockingQueue.poll()).isNull();

        System.out.println("poll start at " + LocalTime.now());
        //使用poll(timeout,unit),空队列,获取元素会阻塞指定时间
        Assertions.assertThat(arrayBlockingQueue.poll(1000, TimeUnit.MILLISECONDS)).isNull();
        Assertions.assertThat(linkedBlockingQueue.poll(1000, TimeUnit.MILLISECONDS)).isNull();
        System.out.println("poll end at " + LocalTime.now());

        //使用take,空队列,获取元素会阻塞
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            arrayBlockingQueue.add(1);
            linkedBlockingQueue.add(1);
        }).start();
        System.out.println("take start at " + LocalTime.now());
        Assertions.assertThat(arrayBlockingQueue.take()).isEqualTo(1);
        Assertions.assertThat(linkedBlockingQueue.take()).isEqualTo(1);
        System.out.println("take end at " + LocalTime.now());
    }


    /**
     * 测试阻塞队列入队
     * @throws Exception exception
     */
    @Test
    public void testBlockingQueueIn() throws Exception {
        //基于数组
        ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(1);
        //基于链表
        LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>(1);
        arrayBlockingQueue.add(1);
        linkedBlockingQueue.add(1);

        //使用add,满队列,添加元素异常
        Assertions.assertThatCode(() -> arrayBlockingQueue.add(1)).isInstanceOf(IllegalStateException.class);
        Assertions.assertThatCode(() -> linkedBlockingQueue.add(1)).isInstanceOf(IllegalStateException.class);

        //使用offer,空队列,获取元素为null
        Assertions.assertThat(arrayBlockingQueue.offer(1)).isEqualTo(false);
        Assertions.assertThat(linkedBlockingQueue.offer(1)).isEqualTo(false);

        System.out.println("offer start at " + LocalTime.now());
        //使用offer(e,timeout,unit),空队列,获取元素会阻塞指定时间
        Assertions.assertThat(arrayBlockingQueue.offer(1, 1000, TimeUnit.MILLISECONDS)).isEqualTo(false);
        Assertions.assertThat(linkedBlockingQueue.offer(1, 1000, TimeUnit.MILLISECONDS)).isEqualTo(false);
        System.out.println("offer end at " + LocalTime.now());

        //使用put,满队列,添加元素会阻塞
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            arrayBlockingQueue.remove();
            linkedBlockingQueue.remove();
        }).start();
        System.out.println("put start at " + LocalTime.now());
        arrayBlockingQueue.put(1);
        linkedBlockingQueue.put(1);
        System.out.println("put end at " + LocalTime.now());
    }

    /**
     * 测试SynchronousQueue
     * @throws InterruptedException Interrupted Exception
     */
    @Test
    public void testSynchronousQueue() throws InterruptedException {
        //队列长度为1,必须有消费者,否则生产者将一直被阻塞
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println(synchronousQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("put start at " + LocalTime.now());
        synchronousQueue.put(1);
        System.out.println("put end at " + LocalTime.now());

    }

    /**
     * 测试优化队列
     */
    @Test
    public void testPriorityBlockingQueue() {
        //优化队列
        PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>(5, Comparator.reverseOrder());
        priorityBlockingQueue.add(1);
        priorityBlockingQueue.add(2);
        priorityBlockingQueue.add(3);
        priorityBlockingQueue.add(4);
        priorityBlockingQueue.add(5);
        while (!priorityBlockingQueue.isEmpty()) {
            System.out.println(priorityBlockingQueue.remove());
        }
    }

    /**
     * 测试延时队列
     * @throws InterruptedException Interrupted Exception
     */
    @Test
    public void testDelayQueue() throws InterruptedException {
        DelayQueue<SimpleDelayed> delayQueue = new DelayQueue<>();
        delayQueue.add(new SimpleDelayed(Instant.now().toEpochMilli() + 3000L, 1));
        delayQueue.add(new SimpleDelayed(Instant.now().toEpochMilli() + 2000L, 2));
        delayQueue.add(new SimpleDelayed(Instant.now().toEpochMilli() + 1000L, 3));
        System.out.println("take start at " + LocalTime.now());
        while (!delayQueue.isEmpty()) {
            //延时时间没过,会一直阻塞
            Integer value = delayQueue.take().getValue();
            System.out.println(LocalTime.now() + ":" + value);
        }
    }


    private static class SimpleDelayed implements Delayed {

        private Long expiry;

        private Integer value;

        SimpleDelayed(Long expiry, Integer value) {
            this.expiry = expiry;
            this.value = value;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return expiry - Instant.now().toEpochMilli();
        }

        @Override
        public int compareTo(Delayed o) {
            long diff = getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
            return (diff == 0 ? 0 : ((diff < 0) ? -1 : 1));
        }

        public Integer getValue() {
            return value;
        }
    }
}
