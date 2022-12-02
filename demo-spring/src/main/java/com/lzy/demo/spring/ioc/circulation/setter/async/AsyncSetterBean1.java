package com.lzy.demo.spring.ioc.circulation.setter.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * 代理注入循环依赖
 *
 * @author lzy
 * @version v1.0
 */
@Component
@EnableAsync
public class AsyncSetterBean1 {
    @Resource
    private AsyncSetterBean2 asyncSetterBean2;

    /**
     * Async task.
     */
    @Async
    public void asyncTask() {
        System.out.println("task");
    }
}
