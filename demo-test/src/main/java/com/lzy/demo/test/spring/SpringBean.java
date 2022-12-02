package com.lzy.demo.test.spring;

import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;

@Component
public class SpringBean {
    /**
     * Instantiates a new Spring bean.
     */
    public SpringBean() {
        print("SpringBean init");
    }

    @PreDestroy
    public void destroy() {
        print("SpringBean destroy");
    }

    private void print(String msg) {
        Thread currentThread = Thread.currentThread();
        System.out.println("thread id:" + currentThread.getId() + ",thread name:" + currentThread.getName() + "," + msg);
    }
}
