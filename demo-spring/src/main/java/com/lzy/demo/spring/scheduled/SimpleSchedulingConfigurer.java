package com.lzy.demo.spring.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

/**
 * 使用SchedulingConfigurer添加定时任务
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class SimpleSchedulingConfigurer implements SchedulingConfigurer {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addCronTask(() -> logger.info("configureTasks"),
                "0/1 * * * * ?");
    }
}
