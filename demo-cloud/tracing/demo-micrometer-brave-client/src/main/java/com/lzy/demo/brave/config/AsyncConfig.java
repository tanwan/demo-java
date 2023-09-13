package com.lzy.demo.brave.config;

import io.micrometer.context.ContextSnapshotFactory;
import org.springframework.aop.interceptor.AsyncExecutionAspectSupport;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.AbstractAsyncConfiguration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

    /**
     * 异步线程使用的Executor
     * 默认是从AsyncExecutionAspectSupport#getDefaultExecutor获取线程池,在TaskExecutionAutoConfiguration#applicationTaskExecutor创建的
     * 如果有存在AsyncConfigurer的bean,则在AbstractAsyncConfiguration获取Executor
     *
     * @param builder builder
     * @return ThreadPoolTaskExecutor
     * @see TaskExecutionAutoConfiguration#applicationTaskExecutor(TaskExecutorBuilder)
     * @see AbstractAsyncConfiguration
     * @see AsyncExecutionAspectSupport#getDefaultExecutor(BeanFactory)
     */
    @Lazy
    @Bean(name = {"applicationTaskExecutor", "taskExecutor"})
    public ThreadPoolTaskExecutor applicationTaskExecutor(TaskExecutorBuilder builder) {
        ContextSnapshotFactory contextSnapshotFactory = ContextSnapshotFactory.builder().build();
        return builder
                .taskDecorator(runnable -> contextSnapshotFactory.captureAll(new Object[0]).wrap(runnable))
                .build();
    }
}
