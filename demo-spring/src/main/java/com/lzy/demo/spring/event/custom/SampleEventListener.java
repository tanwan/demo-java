/*
 * Created by lzy on 2018/8/7 4:58 PM.
 */
package com.lzy.demo.spring.event.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 自定义事件监听
 *
 * @author lzy
 * @version v1.0
 */
@Component
@Slf4j
public class SampleEventListener {
    /**
     * 监听的条件
     *
     * @param sampleEvent the sample event
     */
    @EventListener(value = SampleEvent.class, condition = "#sampleEvent.body == 'hello world'")
    public void conditionListener(SampleEvent sampleEvent) {
        log.info("conditionListener:{}", sampleEvent);
    }


    /**
     * 指定监听的顺序
     *
     * @param sampleEvent the sample event
     * @throws InterruptedException the interrupted exception
     */
    @EventListener(SampleEvent.class)
    @Order(1)
    public void orderListener(SampleEvent sampleEvent) throws InterruptedException {
        log.info("orderListener:{}", sampleEvent);
        Thread.sleep(5000);
    }


    /**
     * Last listener.
     *
     * @param sampleEvent the sample event
     */
    @EventListener(SampleEvent.class)
    @Order(10)
    public void lastListener(SampleEvent sampleEvent) {
        log.info("lastListener:{}", sampleEvent);
    }

    /**
     * 事件监听默认使用当前线程,所以一个监听处理慢的话,其它使用同线程的监听就需要等待这个监听
     * 这里使用异步线程,不受当前线程的影响
     *
     * @param sampleEvent the sample event
     * @throws InterruptedException the interrupted exception
     */
    @EventListener(SampleEvent.class)
    @Async
    public void asyncListener(SampleEvent sampleEvent) throws InterruptedException {
        log.info("asyncListener:{}", sampleEvent);
        Thread.sleep(5000);
    }


    /**
     * 抛出异常的监听,使用异常处理器处理
     *
     * @param sampleEvent the sample event
     * @see CustomErrorHandleConfig#applicationEventMulticaster()
     */
    @EventListener(SampleEvent.class)
    public void exceptionListener(SampleEvent sampleEvent) {
        log.info("exceptionListener:{}", sampleEvent);
        throw new RuntimeException("exceptionListener");
    }
}
