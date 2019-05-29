/*
 * Created by lzy on 2019-05-29 09:52.
 */
package com.lzy.demo.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.concurrent.Future;

/**
 * 测试异步
 *
 * @author lzy
 * @version v1.0
 */
@EnableAsync
@ExtendWith(SpringExtension.class)
@SpringJUnitConfig({AsyncBean.class})
@Slf4j
public class AsyncTest {

    /**
     * 测试没有返回值
     *
     * @param asyncBean the async bean
     * @throws Exception the exception
     */
    @Test
    public void testWithoutReturn(@Autowired AsyncBean asyncBean) throws Exception {
        asyncBean.withoutReturn();
        log.info("testWithoutReturn done");
        Thread.sleep(3000);
    }

    /**
     * 测试有返回值
     *
     * @param asyncBean the async bean
     * @throws Exception the exception
     */
    @Test
    public void testWithReturn(@Autowired AsyncBean asyncBean) throws Exception {
        Future<String> result = asyncBean.withReturn();
        log.info("testWithReturn done");
        log.info("result:{}", result.get());
    }


    /**
     * 测试抛出异常,默认使用SimpleAsyncUncaughtExceptionHandler
     *
     * @param asyncBean the async bean
     * @throws Exception the exception
     */
    @Test
    public void testThrowException(@Autowired AsyncBean asyncBean) throws Exception {
        asyncBean.throwException();
        log.info("testThrowException done");
        Thread.sleep(1000);
    }
}
