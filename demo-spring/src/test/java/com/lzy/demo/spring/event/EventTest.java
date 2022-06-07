package com.lzy.demo.spring.event;

import com.lzy.demo.spring.event.custom.CustomErrorHandleConfig;
import com.lzy.demo.spring.event.custom.SimpleEvent;
import com.lzy.demo.spring.event.custom.SimpleEventListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * spring 事件测试类
 *
 * @author lzy
 * @version v1.0
 */
//开启异步
@EnableAsync
@SpringJUnitConfig({CustomErrorHandleConfig.class, SimpleEventListener.class})
public class EventTest {
    /**
     * 测试自定义事件
     *
     * @param applicationEventPublisher the application event publisher
     */
    @Test
    public void testCustomEvent(@Autowired ApplicationEventPublisher applicationEventPublisher) {
        applicationEventPublisher.publishEvent(new SimpleEvent("hello world"));
        System.out.println("publishEvent finish");
    }
}
