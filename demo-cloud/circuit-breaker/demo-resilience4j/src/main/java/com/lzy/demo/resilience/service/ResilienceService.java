package com.lzy.demo.resilience.service;

import com.lzy.demo.resilience.exception.IgnoreException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class ResilienceService {

    public static final String SIMPLE_BACKEND = "simpleBackend";

    /**
     * 抛出异常
     *
     * @param exception the exception
     * @return the string
     * @throws IOException the io exception
     */
    public String exception(String exception) throws IOException {
        if (IgnoreException.class.getSimpleName().equals(exception)) {
            throw new IgnoreException("expect ignore exception");
        } else if (IOException.class.getSimpleName().equals(exception)) {
            throw new IOException("expect io exception");
        }
        throw new RuntimeException("expect exception");
    }


    /**
     * 慢调用
     *
     * @return the string
     * @throws InterruptedException the interrupted exception
     */
    public String slowCall() throws InterruptedException {
        Thread.sleep(1500);
        return "slow";
    }


    /**
     * 重试
     *
     * @param exception the exception
     * @return the string
     * @throws IOException the io exception
     */
    public String retry(String exception) throws IOException {
        System.out.println("call retry");
        if (new Random().nextInt(10) < 8) {
            return exception(exception);
        } else {
            return "retry";
        }
    }

    /**
     * 舱壁
     *
     * @return the string
     * @throws InterruptedException the interrupted exception
     */
    public String bulkhead() throws InterruptedException {
        Thread.sleep(1000);
        return "bulkhead";
    }


    /**
     * 使用同一个舱壁
     *
     * @return the string
     * @throws InterruptedException the interrupted exception
     */
    public String useSameBulkhead() throws InterruptedException {
        Thread.sleep(1000);
        return "useSameBulkhead";
    }

    /**
     * 使用threadPoolBulkhead
     *
     * @return the completable future
     */
    public CompletableFuture<String> threadPoolBulkhead() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return CompletableFuture.supplyAsync(() -> "threadPoolBulkhead");
    }


    /**
     * 限流
     *
     * @return the string
     */
    public String rateLimit() {
        return "rateLimit";
    }
}
