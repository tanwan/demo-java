package com.lzy.demo.resilience;

import com.lzy.demo.resilience.exception.IgnoreException;
import com.lzy.demo.resilience.service.ResilienceService;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;
import io.github.resilience4j.bulkhead.VavrBulkhead;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.VavrCircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.retry.VavrRetry;
import io.vavr.control.Try;
import org.junit.jupiter.api.Disabled;
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
 * 测试代码使用resilience
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(ThreadEachCallback.class)
public class ResilienceTest extends AbstractResilienceTest {

    @Resource
    private ResilienceService resilienceService;

    @Resource
    private ThreadPoolBulkheadRegistry threadPoolBulkheadRegistry;

    /**
     * 测试抛出异常
     *
     * @param repetitionInfo the repetition info
     */
    @RepeatedTest(10)
    public void testException(RepetitionInfo repetitionInfo) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(ResilienceService.SIMPLE_BACKEND);
        if (repetitionInfo.getCurrentRepetition() <= 5) {
            //熔断未打开的时候,抛出IOException
            assertThatCode(() -> circuitBreaker.executeCheckedSupplier(() -> resilienceService.exception(IOException.class.getSimpleName())))
                    .isInstanceOf(IOException.class);
        } else {
            //熔断打开后,抛出CallNotPermittedException
            assertThatCode(() -> circuitBreaker.executeCheckedSupplier(() -> resilienceService.exception(IOException.class.getSimpleName())))
                    .isInstanceOf(CallNotPermittedException.class);
        }
    }

    /**
     * 测试抛出忽略的异常,不需要解发熔断
     */
    @RepeatedTest(10)
    public void textIgnoreException() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(ResilienceService.SIMPLE_BACKEND);
        assertThatCode(() -> circuitBreaker.executeCheckedSupplier(() -> resilienceService.exception(IgnoreException.class.getSimpleName())))
                .isInstanceOf(IgnoreException.class);
    }

    /**
     * 测试降级
     *
     * @throws IOException the io exception
     */
    @RepeatedTest(10)
    public void testFallback() throws IOException {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(ResilienceService.SIMPLE_BACKEND);
        Try<String> result = Try.of(VavrCircuitBreaker.decorateCheckedSupplier(circuitBreaker, () -> resilienceService.exception(IOException.class.getSimpleName())))
                //熔断器打开后的降级
                .recover(CallNotPermittedException.class, t -> "circuit breaker open")
                //熔断器未打开失败的降级
                .recover(t -> "fallback");
        System.out.println(result.get());
    }

    /**
     * 测试慢调用
     *
     * @param repetitionInfo the repetition info
     * @throws Throwable the throwable
     */
    @RepeatedTest(10)
    public void testSlowCall(RepetitionInfo repetitionInfo) throws Throwable {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(ResilienceService.SIMPLE_BACKEND);
        if (repetitionInfo.getCurrentRepetition() <= 5) {
            //熔断未打开的时候,成功执行
            assertEquals("slow", circuitBreaker.executeCheckedSupplier(() -> resilienceService.slowCall()));
        } else {
            //熔断打开后,抛出CallNotPermittedException
            assertThatCode(() -> circuitBreaker.executeCheckedSupplier(() -> resilienceService.slowCall()))
                    .isInstanceOf(CallNotPermittedException.class);
        }
    }


    /**
     * 测试重试
     *
     * @throws IOException the io exception
     */
    @RepeatedTest(10)
    public void testRetry() throws IOException {
        Try<String> result = Try.of(VavrRetry
                        .decorateCheckedSupplier(
                                retryRegistry.retry(ResilienceService.SIMPLE_BACKEND),
                                VavrCircuitBreaker.decorateCheckedSupplier(circuitBreakerRegistry.circuitBreaker(ResilienceService.SIMPLE_BACKEND),
                                        () -> resilienceService.retry(IOException.class.getSimpleName()))))
                //熔断器未打开失败的降级
                .recover(t -> "retry fallback");
        System.out.println(result.get());
    }

    /**
     * 测试重试和熔断
     *
     * @throws IOException the io exception
     */
    @RepeatedTest(10)
    public void retryWithCircuitBreaker() throws IOException {
        Try<String> result = Try.of(VavrRetry
                        .decorateCheckedSupplier(retryRegistry.retry(ResilienceService.SIMPLE_BACKEND),
                                () -> resilienceService.retry(IOException.class.getSimpleName())))
                //熔断器未打开失败的降级
                .recover(t -> "retry fallback");
        System.out.println(result.get());
    }


    /**
     * 测试动态配置
     */
    @RepeatedTest(1)
    public void testDynamicConfig() {
        changeCircuitBreakerConfig();
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(ResilienceService.SIMPLE_BACKEND);
        for (int i = 0; i < 2; i++) {
            assertThatCode(() -> circuitBreaker.executeCheckedSupplier(() -> resilienceService.exception(IOException.class.getSimpleName())))
                    .isInstanceOf(IOException.class);
        }
        assertThatCode(() -> circuitBreaker.executeCheckedSupplier(() -> resilienceService.exception(IOException.class.getSimpleName())))
                .isInstanceOf(CallNotPermittedException.class);

    }


    /**
     * 测试舱壁
     *
     * @param executorService the executor service
     */
    @RepeatedTest(1)
    public void testBulkhead(ExecutorService executorService) {
        Bulkhead bulkhead = bulkheadRegistry.bulkhead(ResilienceService.SIMPLE_BACKEND);
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    Try<String> result = Try.of(VavrBulkhead.decorateCheckedSupplier(bulkhead, () -> resilienceService.bulkhead()))
                            //降级
                            .recover(Exception.class, t -> "bulkhead fallback");
                    System.out.println(LocalTime.now() + ":" + result.get());
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        }
    }

    /**
     * 测试使用同一个舱壁
     *
     * @param executorService the executor service
     */
    @RepeatedTest(1)
    public void testUseSameBulkhead(ExecutorService executorService) {
        //使用同一个舱壁
        Bulkhead bulkhead = bulkheadRegistry.bulkhead(ResilienceService.SIMPLE_BACKEND);
        for (int i = 0; i < 5; i++) {
            //虽然调用不同的接口,但是因为使用的是同一个舱壁,因此共享同一个并发限制
            executorService.submit(() -> {
                try {
                    System.out.println(LocalTime.now() + ":" + bulkhead.executeCheckedSupplier(() -> resilienceService.bulkhead()));
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
            executorService.submit(() -> {
                try {
                    System.out.println(LocalTime.now() + ":" + bulkhead.executeCheckedSupplier(() -> resilienceService.useSameBulkhead()));
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
        ThreadPoolBulkhead threadPoolBulkhead = threadPoolBulkheadRegistry.bulkhead(ResilienceService.SIMPLE_BACKEND);
        for (int i = 0; i < 10; i++) {
            //超过等待队列的将被丢弃
            executorService.submit(() -> {
                threadPoolBulkhead.executeSupplier(() -> resilienceService.threadPoolBulkhead()
                        .thenAccept(t -> System.out.println(LocalTime.now() + ":" + t))
                );
            });
        }
    }

    /**
     * 测试限流
     */
    @RepeatedTest(1)
    public void testRateLimit() {
        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter(ResilienceService.SIMPLE_BACKEND);
        for (int i = 0; i < 10; i++) {
            Try<String> result = Try.ofSupplier(RateLimiter.decorateSupplier(rateLimiter, () -> resilienceService.rateLimit()))
                    //降级
                    .recover(RequestNotPermitted.class, t -> "rateLimit fallback");
            System.out.println(result.get());
        }
    }
}
