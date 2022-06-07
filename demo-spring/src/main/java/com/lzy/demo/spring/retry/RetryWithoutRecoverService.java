package com.lzy.demo.spring.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 没有Recover方法
 *
 * @author lzy
 * @version v1.0
 */
@Service
public class RetryWithoutRecoverService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 重试一直都抛出异常
     *
     * @param atomicInteger the atomic integer
     */
    @Retryable(backoff = @Backoff(value = 2000, multiplier = 2.0))
    public void retryAlwaysThrowException(AtomicInteger atomicInteger) {
        atomicInteger.incrementAndGet();
        logger.info("retryAlwaysThrowException:{}", atomicInteger.get());
        throw new RuntimeException("expect exception");

    }
}
