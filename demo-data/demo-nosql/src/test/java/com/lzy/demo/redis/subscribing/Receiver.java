package com.lzy.demo.redis.subscribing;

import java.util.concurrent.CountDownLatch;

public class Receiver {
    public static final CountDownLatch countDownLatch = new CountDownLatch(1);

    public void receive(String msg) {
        System.out.println("receive:" + msg);
        if (msg.equals("exit")) {
            countDownLatch.countDown();
        }
    }

    public void receive2(String msg) {
        System.out.println("receive2:" + msg);
    }
}
