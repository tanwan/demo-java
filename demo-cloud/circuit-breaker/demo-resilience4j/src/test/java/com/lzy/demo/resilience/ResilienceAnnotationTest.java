package com.lzy.demo.resilience;

import com.lzy.demo.resilience.exception.IgnoreException;
import com.lzy.demo.resilience.service.ResilienceAnnotationService;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.annotation.Resource;
import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试使用resilience注解
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(ThreadEachCallback.class)
public class ResilienceAnnotationTest extends AbstractResilienceTest {

    @Resource
    private ResilienceAnnotationService resilienceAnnotationService;

    /**
     * 测试抛出异常
     *
     * @param repetitionInfo the repetition info
     */
    @RepeatedTest(10)
    public void testException(RepetitionInfo repetitionInfo) {
        if (repetitionInfo.getCurrentRepetition() <= 5) {
            //熔断未打开的时候,抛出IOException
            assertThatCode(() -> resilienceAnnotationService.exception(IOException.class.getSimpleName()))
                    .isInstanceOf(IOException.class);
        } else {
            //熔断打开后,抛出CallNotPermittedException
            assertThatCode(() -> resilienceAnnotationService.exception(IOException.class.getSimpleName()))
                    .isInstanceOf(CallNotPermittedException.class);
        }
    }


    /**
     * 测试抛出忽略的异常,不需要触发熔断
     */
    @RepeatedTest(10)
    public void textIgnoreException() {
        assertThatCode(() -> resilienceAnnotationService.exception(IgnoreException.class.getSimpleName()))
                .isInstanceOf(IgnoreException.class);
    }

    /**
     * 测试降级
     *
     * @throws IOException the io exception
     */
    @RepeatedTest(10)
    public void testFallback() throws IOException {
        System.out.println(resilienceAnnotationService.exceptionFallback(IOException.class.getSimpleName()));
    }

    /**
     * 测试慢调用
     *
     * @param repetitionInfo the repetition info
     * @throws Throwable the throwable
     */
    @RepeatedTest(10)
    public void testSlowCall(RepetitionInfo repetitionInfo) throws Throwable {
        if (repetitionInfo.getCurrentRepetition() <= 5) {
            //熔断未打开的时候,成功执行
            assertEquals("slow", resilienceAnnotationService.slowCall());
        } else {
            //熔断打开后,抛出CallNotPermittedException
            assertThatCode(() -> resilienceAnnotationService.slowCall())
                    .isInstanceOf(CallNotPermittedException.class);
        }
    }

    /**
     * 测试重试
     */
    @RepeatedTest(10)
    public void testRetry() throws IOException {
        System.out.println(resilienceAnnotationService.retry(IOException.class.getSimpleName()));
    }

    /**
     * 测试重试和熔断
     */
    @RepeatedTest(10)
    public void retryWithCircuitBreaker() throws IOException {
        System.out.println(resilienceAnnotationService.retryWithCircuitBreaker(IOException.class.getSimpleName()));
    }


    /**
     * 测试动态配置
     */
    @RepeatedTest(1)
    public void testDynamicConfig() {
        changeCircuitBreakerConfig();
        for (int i = 0; i < 2; i++) {
            assertThatCode(() -> resilienceAnnotationService.exception(IOException.class.getSimpleName()))
                    .isInstanceOf(IOException.class);
        }
        assertThatCode(() -> resilienceAnnotationService.exception(IOException.class.getSimpleName()))
                .isInstanceOf(CallNotPermittedException.class);
    }

    /**
     * 测试舱壁
     *
     * @param executorService the executor service
     */
    @RepeatedTest(1)
    public void testBulkhead(ExecutorService executorService) {
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    System.out.println(LocalTime.now() + ":" + resilienceAnnotationService.bulkhead());
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        }
    }

    /**
     * 测试舱壁降级
     *
     * @param executorService the executor service
     */
    @RepeatedTest(1)
    public void testBulkheadFallback(ExecutorService executorService) {
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    System.out.println(LocalTime.now() + ":" + resilienceAnnotationService.bulkheadFallback());
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        }
    }

    /**
     * 测试基于固定线程池的舱壁
     *
     * @param executorService the executor service
     */
    @RepeatedTest(1)
    public void testThreadPoolBulkhead(ExecutorService executorService) {
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                resilienceAnnotationService.threadPoolBulkhead()
                        .thenAccept(t -> System.out.println(LocalTime.now() + ":" + t));
            });
        }
    }

    /**
     * 测试限流
     */
    @RepeatedTest(1)
    public void testRateLimit() {
        for (int i = 0; i < 10; i++) {
            System.out.println(resilienceAnnotationService.rateLimit());
        }
    }
}
