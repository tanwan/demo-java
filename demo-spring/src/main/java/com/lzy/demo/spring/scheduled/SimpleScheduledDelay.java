package com.lzy.demo.spring.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 上个任务执行完后执行下一个任务
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class SimpleScheduledDelay {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 上个任务执行后1s执行下一个任务
     *
     * @throws InterruptedException the interrupted exception
     */
    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void fixDelay() throws InterruptedException {
        logger.info("fixDelay");
        Thread.sleep(1000);
    }

}
