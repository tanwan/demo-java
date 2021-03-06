/*
 * Created by lzy on 2018/10/12 1:58 PM.
 */
package com.lzy.demo.spring.ioc.circulation.setter.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 代理注入循环依赖
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class AsyncSetterBean2 {
    @Resource
    private AsyncSetterBean1 asyncSetterBean1;

    /**
     * Async task.
     */
    @Async
    public void asyncTask() {
        System.out.println("task");
    }
}
