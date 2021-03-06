/*
 * Created by lzy on 2019-06-10 14:45.
 */
package com.lzy.demo.spring.retry;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试重试
 *
 * @author LZY
 * @version v1.0
 */
@EnableRetry
@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(classes = {RetryService.class, RetryWithoutRecoverService.class})
public class RetryTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private AtomicInteger atomicInteger;

    /**
     * 初始化
     */
    @BeforeEach
    public void init() {
        atomicInteger = new AtomicInteger();
    }


    /**
     * 测试重试后,不抛出异常
     *
     * @param retryService the retry service
     */
    @Test
    public void testRetryNoThrowException(@Autowired RetryService retryService) {
        retryService.retryNoThrowException(atomicInteger);
        Assertions.assertThat(atomicInteger.get()).isEqualTo(3);
    }

    /**
     * 测试重试后,抛出异常
     *
     * @param retryWithoutRecoverService the retry without recover service
     */
    @Test
    public void testRetryAlwaysThrowException(@Autowired RetryWithoutRecoverService retryWithoutRecoverService) {
        // 这边会抛出异常
        Assertions.assertThatCode(() -> retryWithoutRecoverService.retryAlwaysThrowException(atomicInteger))
                .isInstanceOf(RuntimeException.class).hasMessage("expect exception");
    }

    /**
     * 测试recover
     *
     * @param retryService the retry service
     */
    @Test
    public void testRecover(@Autowired RetryService retryService) {
        retryService.retryRecover(atomicInteger);
    }

    /**
     * 测试有状态的重试
     *
     * @param retryService the retry service
     */
    @Test
    public void testStatefulRetry(@Autowired RetryService retryService) {
        // 每次调用都会抛出异常,因此需要进行异常捕获和重新调用
        for (int i = 0; i < 3; i++) {
            try {
                retryService.statefulRetry(atomicInteger);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        // 再次调用会进入@Recover方法,因为这边已经达到了最大重试次数
        retryService.statefulRetry(atomicInteger);
        // 这边再调用时,相当于重试次数重置了,因此又需要达到最大重试次数时,才会再进入@Recover方法
        for (int i = 0; i < 3; i++) {
            try {
                retryService.statefulRetry(atomicInteger);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * 测试熔断器
     *
     * @param retryService the retry service
     * @throws Exception the exception
     */
    @Test
    public void testCircuitBreaker(@Autowired RetryService retryService) throws Exception {
        for (int i = 1; i <= 3; i++) {
            retryService.circuitBreaker(atomicInteger);
            Assertions.assertThat(atomicInteger.get()).isEqualTo(i);
        }
        // 熔断开启,不会调用真正的方法
        retryService.circuitBreaker(atomicInteger);
        Assertions.assertThat(atomicInteger.get()).isEqualTo(3);

        Thread.sleep(3000);
        // 熔断关闭后
        for (int i = 4; i <= 6; i++) {
            retryService.circuitBreaker(atomicInteger);
            Thread.sleep(2000);
            Assertions.assertThat(atomicInteger.get()).isEqualTo(i);
        }
        // 5s内,没有异常3次,因此熔断没有开启
        retryService.circuitBreaker(atomicInteger);
        Assertions.assertThat(atomicInteger.get()).isEqualTo(7);
    }

    /**
     * 测试使用templateRetry
     *
     * @param retryService the retry service
     */
    @Test
    public void testTemplateRetry(@Autowired RetryService retryService)  {
        retryService.templateRetry(atomicInteger);
    }
}
