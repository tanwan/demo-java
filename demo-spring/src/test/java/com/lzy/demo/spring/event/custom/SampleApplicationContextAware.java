/*
 * Created by lzy on 2018/8/7 5:27 PM.
 */
package com.lzy.demo.spring.event.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * 自定义事件发布者
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class SampleApplicationContextAware implements ApplicationEventPublisherAware {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void sendEvent(String eventName) {
        logger.info("------------------ send event start -----------------------------");
        applicationEventPublisher.publishEvent(new SampleEvent(eventName));
        logger.info("------------------ send event end -----------------------------");
    }
}
