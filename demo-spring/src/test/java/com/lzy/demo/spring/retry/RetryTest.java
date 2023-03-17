package com.lzy.demo.spring.retry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试重试
 *
 * @author LZY
 * @version v1.0
 */
@EnableRetry
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
        assertEquals(3, atomicInteger.get());
    }

    /**
     * 测试重试后,抛出异常
     *
     * @param retryWithoutRecoverService the retry without recover service
     */
    @Test
    public void testRetryAlwaysThrowException(@Autowired RetryWithoutRecoverService retryWithoutRecoverService) {
        // 这边会抛出异常
        assertThatCode(() -> retryWithoutRecoverService.retryAlwaysThrowException(atomicInteger))
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
            assertEquals(i, atomicInteger.get());
        }
        // 熔断开启,不会调用真正的方法
        retryService.circuitBreaker(atomicInteger);
        assertEquals(3, atomicInteger.get());

        Thread.sleep(3000);
        // 熔断关闭后
        for (int i = 4; i <= 6; i++) {
            retryService.circuitBreaker(atomicInteger);
            Thread.sleep(2000);
            assertEquals(i, atomicInteger.get());
        }
        // 5s内,没有异常3次,因此熔断没有开启
        retryService.circuitBreaker(atomicInteger);
        assertEquals(7, atomicInteger.get());
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
