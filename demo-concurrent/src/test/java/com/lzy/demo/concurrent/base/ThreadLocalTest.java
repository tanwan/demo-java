/*
 * Created by LZY on 2016-10-26 21:45.
 */
package com.lzy.demo.concurrent.base;

import org.junit.jupiter.api.Test;

/**
 * ThreadLocal
 *
 * @author LZY
 * @version v1.0
 */
public class ThreadLocalTest {

    /**
     * 测试ThreadLocal
     */
    @Test
    public void testThreadLocal() {
        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        new Thread(() -> {
            try {
                if (threadLocal.get() == null) {
                    //如果ThreadLocal存放的是对象,则不能为每个线程分配相同的实例对象,否则ThreadLocal也不能保证线程安全
                    threadLocal.set(1);
                }
                System.out.println(threadLocal.get());
            } finally {
                // 最后,需要进行释放
                threadLocal.remove();
            }
        }).start();
    }

    /**
     * ThreadLocal不支持继承
     */
    @Test
    public void testThreadLocalInherit() {
        testInherit(new ThreadLocal<>());
    }

    /**
     * InheritableThreadLocal支持继承
     */
    @Test
    public void testInheritableThreadLocal() {
        testInherit(new InheritableThreadLocal<>());
    }

    private void testInherit(ThreadLocal<Integer> threadLocal) {
        new Thread(() -> {
            try {
                if (threadLocal.get() == null) {
                    //如果ThreadLocal存放的是对象,则不能为每个线程分配相同的实例对象,否则ThreadLocal也不能保证线程安全
                    threadLocal.set(1);
                }
                // 子线程无法继承父线程的ThreadLocal
                new Thread(() -> System.out.println("thread2: " + threadLocal.get())).start();
            } finally {
                // 最后,需要进行释放
                threadLocal.remove();
            }
        }).start();
    }
}
