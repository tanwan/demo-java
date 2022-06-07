package com.lzy.demo.base.concurrent.base;

import org.junit.jupiter.api.Test;

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
                    threadLocal.set(1);
                }
                System.out.println(threadLocal.get());
            } finally {
                // 最后,需要进行释放
                threadLocal.remove();
            }
        }).start();
        new Thread(() -> {
            try {
                //只能获取到本线程设置的值,因此这边第一次get会返回null
                if (threadLocal.get() == null) {
                    threadLocal.set(2);
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
