/*
 * Created by lzy on 2018/8/7 4:58 PM.
 */
package com.lzy.demo.spring.event.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class SampleEventListener {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 继承ApplicationListener,也可以使用@EventListener
     *
     * @param event the event to respond to
     */
    //@Override
    public void onApplicationEvent(SampleEvent event) {
        logger.info("onApplicationEvent:{}", event);
    }
    /**
     * 监听的条件
     *
     * @param sampleEvent the sample event
     */
    @EventListener(value = SampleEvent.class, condition = "#sampleEvent.source == 'sampleEvent'")
    @Order(2)
    public void conditionListener(SampleEvent sampleEvent) throws Exception {
        logger.info("conditionListener:{}", sampleEvent);
        throw new Exception("exception");
    }
    /**
     * 指定监听的顺序
     *
     * @param sampleEvent the sample event
     */
    @EventListener(SampleEvent.class)
    //@Order(1)
    public void orderListener(SampleEvent sampleEvent) throws InterruptedException {
        logger.info("orderListener:{}", sampleEvent);
        Thread.sleep(5000);

    }



    /**
     * @param sampleEvent the sample event
     */
    @EventListener(SampleEvent.class)
    @Order(10)
    public void lastListener(SampleEvent sampleEvent) throws Exception {
        logger.info("lastListener:{}", sampleEvent);
    }

    /**
     * spring的事件处理是单线程的,所以如果一个事件被触发,除非所有的监听者接收到消息并全部处理完,否则这个进程被阻塞
     * 这里使用异步处理事件
     *
     * @param sampleEvent the sample event
     */
    @EventListener(SampleEvent.class)
    @Async
    public void asyncListener(SampleEvent sampleEvent) throws InterruptedException {
        logger.info("asyncListener:{}", sampleEvent);
        Thread.sleep(5000);
    }
}
