/*
 * Created by lzy on 2018/8/6 9:05 PM.
 */
package com.lzy.demo.spring.event;

import com.lzy.demo.spring.event.custom.CustomErrorHandleConfig;
import com.lzy.demo.spring.event.custom.SampleEvent;
import com.lzy.demo.spring.event.custom.SampleEventListener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * spring 事件测试类
 *
 * @author lzy
 * @version v1.0
 */
//开启异步
@EnableAsync
@ExtendWith(SpringExtension.class)
@SpringJUnitConfig({CustomErrorHandleConfig.class, SampleEventListener.class})
public class EventTest {
    /**
     * 测试自定义事件
     *
     * @param applicationEventPublisher the application event publisher
     */
    @Test
    public void testCustomEvent(@Autowired ApplicationEventPublisher applicationEventPublisher) {
        applicationEventPublisher.publishEvent(new SampleEvent("hello world"));
        System.out.println("publishEvent finish");
    }
}
