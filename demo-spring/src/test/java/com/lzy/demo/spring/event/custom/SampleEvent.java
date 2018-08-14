/*
 * Created by lzy on 2018/8/7 11:20 AM.
 */
package com.lzy.demo.spring.event.custom;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义事件
 *
 * @author lzy
 * @version v1.0
 */
public class SampleEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public SampleEvent(Object source) {
        super(source);
    }
}
