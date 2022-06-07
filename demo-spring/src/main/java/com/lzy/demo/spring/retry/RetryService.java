package com.lzy.demo.spring.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RetryService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 如果发生异常,将重试3遍(默认)
     *
     * @param atomicInteger the atomic integer
     */
    @Retryable(backoff = @Backoff(value = 2000, multiplier = 2.0))
    public void retryNoThrowException(AtomicInteger atomicInteger) {
        atomicInteger.incrementAndGet();
        logger.info("retryNoThrowException:{}", atomicInteger.get());
        // 模拟第3次重次不抛出异常
        if (atomicInteger.get() < 3) {
            throw new RuntimeException("expect exception");
        }
    }

    /**
     * 重试都出现异常的话,则会调用本类@Recover标注的方法
     *
     * @param atomicInteger the atomic integer
     */
    @Retryable(backoff = @Backoff(value = 2000, multiplier = 2.0))
    public void retryRecover(AtomicInteger atomicInteger) {
        atomicInteger.incrementAndGet();
        logger.info("retryRecover:{}", atomicInteger.get());
        throw new RuntimeException("expect exception");
    }

    /**
     * 使用stateful=true表示有状态的重试,有事务的考虑使用
     * 有状态,其实就是重试次数和上次执行失败的时间有状态,当重试到最大次数后,重新次数会进行重置,然后在进行重试的时候,会根据上一次失败执行的时间来确定下一次执行的时间
     * 这个方法的异常每次调用都会抛出去,因此,调用方必须进行异常捕获和重新调用此方法
     *
     * @param atomicInteger the atomic integer
     */
    @Retryable(stateful = true, backoff = @Backoff(value = 2000, multiplier = 2.0))
    public void statefulRetry(AtomicInteger atomicInteger) {
        atomicInteger.incrementAndGet();
        logger.info("retryNoThrowException:{}", atomicInteger.get());
        //这里的异常每次调用会抛出去
        throw new RuntimeException("expect exception");

    }

    /**
     * 熔断器本质上也是@Retryable,当在openTimeout(默认5s)失败达到maxAttempts(默认3次),则熔断resetTimeout(默认20s)
     * 在熔断生效的这段时间内,所以调用该方法的都会去调用@Recover标注的方法
     *
     * @param atomicInteger the atomic integer
     */
    @CircuitBreaker(resetTimeout = 2000L)
    public void circuitBreaker(AtomicInteger atomicInteger) {
        atomicInteger.incrementAndGet();
        logger.info("circuitBreaker:{}", atomicInteger.get());
        throw new RuntimeException("expect exception");
    }


    /**
     * 使用RetryTemplate
     *
     * @param atomicInteger the atomic integer
     */
    public void templateRetry(AtomicInteger atomicInteger) {
        RetryTemplate template = new RetryTemplate();
        // 超时重试策略,在5秒之内一直重试
        TimeoutRetryPolicy policy = new TimeoutRetryPolicy();
        policy.setTimeout(5000L);
        // 退避算法
        BackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        template.setBackOffPolicy(backOffPolicy);
        template.setRetryPolicy(policy);
        template.execute(context -> {
            //这里可以设置一些属性
            context.setAttribute("param", atomicInteger.get());
            atomicInteger.incrementAndGet();
            logger.info("templateRetry:{}", atomicInteger.get());
            throw new RuntimeException("expect exception");
        }, context -> {
            //这里可以取到重试之前设置的属性
            logger.info("param:{}", context.getAttribute("param"));
            //获取到异常
            logger.info("recovery callback:{}", context.getLastThrowable().getMessage());
            return null;
        });
    }


    /**
     * 本类的方法重试失败后会调用@Recover标注的方法,如果本类存在多个@Recover注解的方法,只会调用到异常类型最匹配的方法
     * 方法的第一个参数为异常,后面的参数就是@Retryable标注的方法的入参
     * 如果存在多个重试方法对应同一个Recover方法的时候,那么重试方法的参数需要保持一致
     *
     * @param e             the e
     * @param atomicInteger the atomic integer
     */
    @Recover
    public void recover(Exception e, AtomicInteger atomicInteger) {
        logger.error("recover:atomicInteger:{},{}", atomicInteger.get(), e.getMessage());
    }

}
