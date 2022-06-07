package com.lzy.demo.resilience;

import com.lzy.demo.resilience.event.ResilienceEvent;
import com.lzy.demo.resilience.service.ResilienceService;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;

import javax.annotation.Resource;
import java.time.LocalTime;

public abstract class AbstractResilienceTest {

    @Resource
    protected CircuitBreakerRegistry circuitBreakerRegistry;

    @Resource
    protected RetryRegistry retryRegistry;

    @Resource
    protected BulkheadRegistry bulkheadRegistry;

    @Resource
    protected RateLimiterRegistry rateLimiterRegistry;

    /**
     * 添加事件
     */
    @BeforeAll
    public void addEvent() {
        ResilienceEvent.addCircuitBreakerListener(circuitBreakerRegistry.circuitBreaker(ResilienceService.SIMPLE_BACKEND));
        ResilienceEvent.addRetryListener(retryRegistry.retry(ResilienceService.SIMPLE_BACKEND));
        ResilienceEvent.addBulkheadListener(bulkheadRegistry.bulkhead(ResilienceService.SIMPLE_BACKEND));
        ResilienceEvent.addRateLimiterListener(rateLimiterRegistry.rateLimiter(ResilienceService.SIMPLE_BACKEND));
    }

    /**
     * 因为这边使用的是同一个CircuitBreaker,所以需要进行重置
     * 可以使用testInfo和Set来判断,也可以使用repetitionInfo的次数来判断
     *
     * @param testInfo       testInfo
     * @param repetitionInfo repetitionInfo
     */
    @BeforeEach
    public void reset(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        if (repetitionInfo.getCurrentRepetition() == 1) {
            circuitBreakerRegistry.circuitBreaker(ResilienceService.SIMPLE_BACKEND).reset();
        }
    }


    /**
     * 熔断状态
     */
    @AfterEach
    public void showCircuitBreakerStatus() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(ResilienceService.SIMPLE_BACKEND);
        CircuitBreaker.Metrics metrics = circuitBreaker.getMetrics();
        // 当前失败率,没计算失败率时为-1
        float failureRate = metrics.getFailureRate();
        // 当前慢调用率,没计算前时-1
        float slowCallRate = metrics.getSlowCallRate();
        // 当前慢调用次数
        int numberOfSlowCalls = metrics.getNumberOfSlowCalls();
        // 当前慢调用成功次数
        int numberOfSlowSuccessfulCalls = metrics.getNumberOfSlowSuccessfulCalls();
        // 当前慢调用失败次数
        int numberOfSlowFailedCalls = metrics.getNumberOfSlowFailedCalls();
        // 熔断之前的调用次数
        int bufferedCalls = metrics.getNumberOfBufferedCalls();
        // 熔断之前的失败调用数
        int failedCalls = metrics.getNumberOfFailedCalls();
        // 当前的成功调用数
        int successCalls = metrics.getNumberOfSuccessfulCalls();
        // 当前不允许执行的调用数
        long notPermittedCalls = metrics.getNumberOfNotPermittedCalls();
        System.out.println(LocalTime.now() + " state=" + circuitBreaker.getState() +
                ", metrics[ failureRate=" + failureRate +
                ", slowCallRate=" + slowCallRate +
                ", numberOfSlowCalls=" + numberOfSlowCalls +
                ", numberOfSlowSuccessfulCalls=" + numberOfSlowSuccessfulCalls +
                ", numberOfSlowFailedCalls=" + numberOfSlowFailedCalls +
                ", bufferedCalls=" + bufferedCalls +
                ", failedCalls=" + failedCalls +
                ", successCalls=" + successCalls +
                ", notPermittedCalls=" + notPermittedCalls +
                " ]");
    }

    /**
     * 修改熔断器配置
     */
    protected void changeCircuitBreakerConfig() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                //最小调用次数修改为2
                .minimumNumberOfCalls(2)
                .slidingWindowSize(10)
                //使用断言判断是否记录异常
                .recordException(e -> true)
                .build();
        //先删除原有的配置
        circuitBreakerRegistry.find(ResilienceService.SIMPLE_BACKEND)
                .ifPresent(circuitBreaker -> circuitBreakerRegistry.remove(ResilienceService.SIMPLE_BACKEND));
        //再添加新的配置
        circuitBreakerRegistry.circuitBreaker(ResilienceService.SIMPLE_BACKEND, circuitBreakerConfig);
    }
}
