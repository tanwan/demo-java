package com.lzy.demo.spring.scheduled;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 配置定时任务执行器
 *
 * @author lzy
 * @version v1.0
 */
@Configuration
public class ScheduledConfig {

    /**
     * 可以使用ScheduledExecutorService(最终会包装成ConcurrentTaskScheduler),也可以直接使用TaskScheduler
     *
     * @return the scheduled executor service
     * @see ScheduledTaskRegistrar#setScheduler(java.lang.Object) ScheduledTaskRegistrar#setScheduler(java.lang.Object)
     */
    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(4);
    }
}
