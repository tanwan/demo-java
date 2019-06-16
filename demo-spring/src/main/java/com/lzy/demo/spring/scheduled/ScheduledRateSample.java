/*
 * Created by lzy on 2019-06-16 07:44.
 */
package com.lzy.demo.spring.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 固定频率执行任务
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class ScheduledRateSample {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 每隔1s执行一次任务
     */
    @Scheduled(fixedRate = 1000, initialDelay = 500)
    public void fixRate() throws InterruptedException {
        logger.info("fixDelay");
        Thread.sleep(500);
    }
}
