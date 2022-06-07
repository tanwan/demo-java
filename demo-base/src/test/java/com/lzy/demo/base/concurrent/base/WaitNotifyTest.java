package com.lzy.demo.base.concurrent.base;

import com.lzy.demo.base.ThreadEachCallback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalTime;

@ExtendWith(ThreadEachCallback.class)
public class WaitNotifyTest {
    private static final Object object = new Object();

    /**
     * 测试 wait和notify
     *
     * @throws Exception the exception
     */
    @Test
    public void testWaitNotify() throws Exception {
        Thread thread1 = new Thread(() -> {
            System.out.println(LocalTime.now() + ":thread1 start");
            // wait需要在synchronized块里,说明当前线程获取到该object的锁
            synchronized (object) {
                try {
                    System.out.println(LocalTime.now() + ":thread1 wait");
                    //先释放该线程拥有的object对象锁(要保证该线程拥有该对象的锁),
                    //然后再等待(需要唤醒,或者wait有传入最大等待时间,否则将会无限等待)
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(LocalTime.now() + ":thread1 complete!");
            }
        });
        Thread thread2 = new Thread(() -> {
            System.out.println(LocalTime.now() + ":thread2 start");
            // notify需要在synchronized块里,说明当前线程获取到该object的锁
            synchronized (object) {
                // 随机唤醒一个正在等待该对象的线程
                System.out.println(LocalTime.now() + ":thread2 notify");
                // notify并不会释放锁
                object.notify();
                //object.notifyAll();唤醒所有等待该对象的线程
                //这两个方法并不决定哪个线程能够获取到该对象
                try {
                    //sleep()不会释放锁,所以被唤醒的线程还需要等到这个线程释放锁后,
                    //才能获取object的锁继承执行
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(LocalTime.now() + ":thread2 complete");
        }
        );
        thread1.start();
        //thread2要在thread1之后运行
        Thread.sleep(500);
        thread2.start();
    }
}
