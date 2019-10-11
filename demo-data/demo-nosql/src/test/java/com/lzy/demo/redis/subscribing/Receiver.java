/*
 * Created by lzy on 9/7/17.
 */
package com.lzy.demo.redis.subscribing;

import java.util.concurrent.CountDownLatch;

/**
 * 消息接收者
 *
 * @author lzy
 * @version v1.0
 */
public class Receiver {
    public static CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * Receiver.
     *
     * @param msg the msg
     */
    public void receive(String msg) {
        System.out.println("receive:" + msg);
        if (msg.equals("exit")) {
            countDownLatch.countDown();
        }
    }


    /**
     * Receive 2.
     *
     * @param msg the msg
     */
    public void receive2(String msg) {
        System.out.println("receive2:" + msg);
    }
}
