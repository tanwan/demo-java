package com.lzy.demo.spring.event.custom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.support.TaskUtils;

/**
 * 添加自定义处理器
 *
 * @author lzy
 * @version v1.0
 */
@Configuration
public class CustomErrorHandleConfig {

    /**
     * bean的名称只能叫applicationEventMulticaster
     *
     * @return the simple application event multicaster
     * @see org.springframework.context.support.AbstractApplicationContext#initApplicationEventMulticaster
     */
    @Bean
    public SimpleApplicationEventMulticaster applicationEventMulticaster() {
        SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();
        simpleApplicationEventMulticaster.setErrorHandler(TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER);
        return simpleApplicationEventMulticaster;
    }
}
