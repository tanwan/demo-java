package com.lzy.demo.spring.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SimpleScheduledCron {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 使用cron
     */
    @Scheduled(cron = "0/1 * * * * ?")
    public void perSecond() {
        logger.info("perSecond");
    }

    /**
     * 执行时间大于调用频率,执行的时候,发现上一次的任务还未结束,则此次任务不执行,等待下一次执行
     *
     * @throws InterruptedException the interrupted exception
     */
    @Scheduled(cron = "0/1 * * * * ?")
    public void spendMoreThanRate() throws InterruptedException {
        logger.info("spendMoreThanRate");
        Thread.sleep(1500);
    }
}
