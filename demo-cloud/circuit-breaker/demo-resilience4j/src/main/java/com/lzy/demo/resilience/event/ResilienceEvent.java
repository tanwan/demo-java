package com.lzy.demo.resilience.event;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.retry.Retry;

/**
 * 事件
 *
 * @author lzy
 * @version v1.0
 */
public class ResilienceEvent {


    /**
     * 添加熔断器事件
     *
     * @param circuitBreaker the circuit breaker
     */
    public static void addCircuitBreakerListener(CircuitBreaker circuitBreaker) {
        circuitBreaker.getEventPublisher()
                .onSuccess(event -> System.out.println("call success:" + event))
                .onError(event -> System.out.println("cal failed:" + event))
                .onIgnoredError(event -> System.out.println("call failed ignore:" + event))
                .onReset(event -> System.out.println("circuitBreaker reset:" + event))
                .onStateTransition(event -> System.out.println("circuitBreaker change:" + event))
                .onCallNotPermitted(event -> System.out.println("circuitBreaker open:" + event));
    }

    /**
     * 添加重试事件
     *
     * @param retry the retry
     */
    public static void addRetryListener(Retry retry) {
        retry.getEventPublisher()
                .onSuccess(event -> System.out.println("call success:" + event))
                .onRetry(event -> System.out.println("retry:" + event))
                .onError(event -> System.out.println("call failed:" + event))
                .onIgnoredError(event -> System.out.println("call failed ignore:" + event));
    }


    /**
     * 添加舱壁事件
     *
     * @param bulkhead the bulkhead
     */
    public static void addBulkheadListener(Bulkhead bulkhead) {
        bulkhead.getEventPublisher()
                .onCallFinished(event -> System.out.println("call finish:" + event))
                .onCallPermitted(event -> System.out.println("call permitted:" + event))
                .onCallRejected(event -> System.out.println("call rejected:" + event));
    }

    /**
     * 添加限流事件
     *
     * @param rateLimiter the rate limiter
     */
    public static void addRateLimiterListener(RateLimiter rateLimiter) {
        rateLimiter.getEventPublisher()
                .onSuccess(event -> System.out.println("call success:" + event))
                .onFailure(event -> System.out.println("call failed:" + event));
    }
}
