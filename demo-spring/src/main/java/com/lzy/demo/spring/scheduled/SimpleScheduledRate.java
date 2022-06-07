package com.lzy.demo.spring.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 固定周期执行任务
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class SimpleScheduledRate {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 每隔1s执行一次任务
     *
     * @throws InterruptedException the interrupted exception
     */
    @Scheduled(fixedRate = 1000, initialDelay = 500)
    public void fixRate() throws InterruptedException {
        logger.info("fixDelay");
        Thread.sleep(500);
    }

    /**
     * 执行时间大于调用频率,执行的时候,发现上一次的任务还未结束,则此次任务不执行,等待下一次执行
     *
     * @throws InterruptedException the interrupted exception
     */
    @Scheduled(fixedRate = 1000, initialDelay = 500)
    public void spendMoreThanRate() throws InterruptedException {
        logger.info("spendMoreThanRate");
        Thread.sleep(1500);
    }
}
