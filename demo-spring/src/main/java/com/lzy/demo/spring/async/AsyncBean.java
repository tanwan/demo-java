/*
 * Created by lzy on 2019-05-29 09:53.
 */
package com.lzy.demo.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * The type Async bean.
 *
 * @author lzy
 * @version v1.0
 */
@Component
@Slf4j
public class AsyncBean {

    /**
     * 执行没有返回值的
     *
     * @throws InterruptedException the interrupted exception
     */
    @Async
    public void withoutReturn() throws InterruptedException {
        Thread.sleep(2000);
        log.info("withoutReturn done");
    }

    /**
     * 执行没有返回值的,返回值需要使用Future
     *
     * @return the future
     * @throws InterruptedException the interrupted exception
     */
    @Async
    public Future<String> withReturn() throws InterruptedException {
        Thread.sleep(2000);
        log.info("withReturn done");
        return new AsyncResult<>("success");
    }


    /**
     * 抛出异常
     */
    @Async
    public void throwException() {
        throw new RuntimeException("throwException");
    }


    /**
     * 使用指定执行器
     *
     * @throws InterruptedException the interrupted exception
     */
    @Async("simpleAsyncTaskExecutor")
    public void useSpecifiedExecutor() throws InterruptedException {
        Thread.sleep(2000);
        log.info("useSpecifiedExecutor done");
    }
}
