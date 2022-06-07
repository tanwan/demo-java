package com.lzy.demo.base.concurrent.base;

import org.junit.jupiter.api.Test;

public class InterruptedTest {

    /**
     * 测试中断
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testInterrupted() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                //判断当前线程的中断标志
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Interruted");
                    break;
                }
                try {
                    System.out.println("thread run");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted when sleep");
                    //Thread.sleep()由于中断抛出异常会清除中断标志,因此循环内的Thread.currentThread().isInterrupted()==false
                    //所以在这个catch块里还要调用interrupt()
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
        Thread.sleep(1000);
        //thread会停止sleep并抛出InterruptedException
        thread.interrupt();
        Thread.sleep(300);
    }
}
