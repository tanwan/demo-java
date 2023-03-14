package com.lzy.demo.resilience.service;

import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class ResilienceAnnotationService extends ResilienceService {


    /**
     * 抛出异常
     *
     * @param exception the exception
     * @return the string
     * @throws IOException the io exception
     * @see io.github.resilience4j.spring6.circuitbreaker.configure.CircuitBreakerAspect
     */
    @CircuitBreaker(name = ResilienceService.SIMPLE_BACKEND)
    @Override
    public String exception(String exception) throws IOException {
        return super.exception(exception);
    }

    /**
     * 抛出异常,降级
     *
     * @param exception the exception
     * @return the string
     * @throws IOException the io exception
     * @see io.github.resilience4j.spring6.circuitbreaker.configure.CircuitBreakerAspect
     */
    @CircuitBreaker(name = ResilienceService.SIMPLE_BACKEND, fallbackMethod = "exceptionFallback")
    public String exceptionFallback(String exception) throws IOException {
        return super.exception(exception);
    }


    /**
     * 慢调用
     *
     * @return the string
     * @throws InterruptedException the interrupted exception
     * @see io.github.resilience4j.spring6.circuitbreaker.configure.CircuitBreakerAspect
     */
    @CircuitBreaker(name = ResilienceService.SIMPLE_BACKEND)
    @Override
    public String slowCall() throws InterruptedException {
        return super.slowCall();
    }

    /**
     * 重试,fallbackMethod不会进入熔断的fallbackMethod
     *
     * @param exception the exception
     * @return the string
     * @throws IOException the io exception
     * @see io.github.resilience4j.spring6.retry.configure.RetryAspect
     */
    @Retry(name = ResilienceService.SIMPLE_BACKEND, fallbackMethod = "exceptionFallback")
    @Override
    public String retry(String exception) throws IOException {
        return super.retry(exception);
    }

    /**
     * 重试和熔断一起,会进入熔断的fallbackMethod,并且重试次数也算在熔断器的调用次数中
     *
     * @param exception the exception
     * @return the string
     * @throws IOException the io exception
     * @see io.github.resilience4j.spring6.retry.configure.RetryAspect
     */
    @CircuitBreaker(name = ResilienceService.SIMPLE_BACKEND)
    @Retry(name = ResilienceService.SIMPLE_BACKEND, fallbackMethod = "exceptionFallback")
    public String retryWithCircuitBreaker(String exception) throws IOException {
        return super.retry(exception);
    }

    /**
     * 舱壁
     *
     * @return the string
     * @see io.github.resilience4j.spring6.bulkhead.configure.BulkheadAspect
     */
    @Override
    @Bulkhead(name = ResilienceService.SIMPLE_BACKEND)
    public String bulkhead() throws InterruptedException {
        return super.bulkhead();
    }

    /**
     * 舱壁降级
     *
     * @return the string
     * @throws InterruptedException the interrupted exception
     * @see io.github.resilience4j.spring6.bulkhead.configure.BulkheadAspect
     */
    @Bulkhead(name = ResilienceService.SIMPLE_BACKEND, fallbackMethod = "bulkheadFallback")
    public String bulkheadFallback() throws InterruptedException {
        return super.useSameBulkhead();
    }

    /**
     * 使用threadPoolBulkhead,返回值必须是CompletableFuture
     *
     * @return the completable future
     * @see io.github.resilience4j.spring6.bulkhead.configure.BulkheadAspect
     */
    @Bulkhead(name = ResilienceService.SIMPLE_BACKEND, fallbackMethod = "threadPoolBulkheadFallback", type = Bulkhead.Type.THREADPOOL)
    @Override
    public CompletableFuture<String> threadPoolBulkhead() {
        return super.threadPoolBulkhead();
    }

    /**
     * 限流
     *
     * @return the string
     */
    @RateLimiter(name = ResilienceService.SIMPLE_BACKEND, fallbackMethod = "rateLimitFallback")
    @Override
    public String rateLimit() {
        return super.rateLimit();
    }

    /**
     * 熔断之后的降级
     *
     * @param exception the exception
     * @param e         the e
     * @return string string
     */
    public String exceptionFallback(String exception, CallNotPermittedException e) {
        return "circuit breaker open";
    }

    /**
     * 失败的降级
     *
     * @param exception the exception
     * @param e         the e
     * @return the string
     */
    public String exceptionFallback(String exception, Exception e) {
        return "fallback";
    }


    /**
     * 舱壁的降级
     *
     * @param e the e
     * @return the string
     */
    public String bulkheadFallback(BulkheadFullException e) {
        return "bulkheadFallback";
    }

    /**
     * 使用threadPoolBulkhead的降级
     *
     * @param e the e
     * @return the completable future
     */
    public CompletableFuture<String> threadPoolBulkheadFallback(Exception e) {
        return CompletableFuture.supplyAsync(() -> "threadPoolBulkheadFallback");
    }

    /**
     * 限制的降级
     *
     * @param e the e
     * @return the string
     */
    public String rateLimitFallback(RequestNotPermitted e) {
        return "rateLimitFallback";
    }
}
