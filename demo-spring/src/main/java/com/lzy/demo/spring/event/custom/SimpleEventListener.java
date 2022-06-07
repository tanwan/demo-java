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
public class SimpleEventListener {
    /**
     * 监听的条件
     *
     * @param simpleEvent the simple event
     */
    @EventListener(value = SimpleEvent.class, condition = "#simpleEvent.body == 'hello world'")
    public void conditionListener(SimpleEvent simpleEvent) {
        log.info("conditionListener:{}", simpleEvent);
    }


    /**
     * 指定监听的顺序
     *
     * @param simpleEvent the simple event
     * @throws InterruptedException the interrupted exception
     */
    @EventListener(SimpleEvent.class)
    @Order(1)
    public void orderListener(SimpleEvent simpleEvent) throws InterruptedException {
        log.info("orderListener:{}", simpleEvent);
        Thread.sleep(5000);
    }


    /**
     * Last listener.
     *
     * @param simpleEvent the simple event
     */
    @EventListener(SimpleEvent.class)
    @Order(10)
    public void lastListener(SimpleEvent simpleEvent) {
        log.info("lastListener:{}", simpleEvent);
    }

    /**
     * 事件监听默认使用当前线程,所以一个监听处理慢的话,其它使用同线程的监听就需要等待这个监听
     * 这里使用异步线程,不受当前线程的影响
     *
     * @param simpleEvent the simple event
     * @throws InterruptedException the interrupted exception
     */
    @EventListener(SimpleEvent.class)
    @Async
    public void asyncListener(SimpleEvent simpleEvent) throws InterruptedException {
        log.info("asyncListener:{}", simpleEvent);
        Thread.sleep(5000);
    }


    /**
     * 抛出异常的监听,使用异常处理器处理
     *
     * @param simpleEvent the simple event
     * @see CustomErrorHandleConfig#applicationEventMulticaster()
     */
    @EventListener(SimpleEvent.class)
    public void exceptionListener(SimpleEvent simpleEvent) {
        log.info("exceptionListener:{}", simpleEvent);
        throw new RuntimeException("exceptionListener");
    }
}
