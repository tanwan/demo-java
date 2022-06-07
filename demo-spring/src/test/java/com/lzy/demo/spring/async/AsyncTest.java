package com.lzy.demo.spring.async;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.concurrent.Future;

/**
 * 测试异步
 *
 * @author lzy
 * @version v1.0
 */
@EnableAsync
@SpringJUnitConfig({AsyncBean.class})
@ExtendWith(ThreadEachCallback.class)
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
        System.out.println("testWithoutReturn done");
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
        System.out.println("testWithReturn done");
        System.out.println("result:" + result.get());
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
        System.out.println("testThrowException done");
    }
}
