package com.lzy.demo.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * 测试使用自定义的异步
 *
 * @author lzy
 * @version v1.0
 */
@EnableAsync
@SpringJUnitConfig({AsyncBean.class, CustomAsyncConfig.class})
@Slf4j
@ExtendWith(ThreadEachCallback.class)
public class CustomAsyncConfigTest {

    /**
     * 测试使用自定义线程池(默认使用的是SimpleAsyncTaskExecutor,每次都创建新的线程)
     *
     * @param asyncBean the async bean
     * @throws Exception the exception
     */
    @Test
    public void testExecutor(@Autowired AsyncBean asyncBean) throws Exception {
        asyncBean.withoutReturn();
        log.info("testExecutor done");
    }

    /**
     * 测试抛出异常
     *
     * @param asyncBean the async bean
     * @throws Exception the exception
     */
    @Test
    public void testThrowException(@Autowired AsyncBean asyncBean) throws Exception {
        asyncBean.throwException();
        log.info("testThrowException done");
    }

    /**
     * 测试抛出异常
     *
     * @param asyncBean the async bean
     * @throws Exception the exception
     */
    @Test
    public void testUseSpecifiedExecutor(@Autowired AsyncBean asyncBean) throws Exception {
        // 使用ThreadPoolTaskExecutor
        asyncBean.withoutReturn();
        // 使用SimpleAsyncTaskExecutor
        asyncBean.useSpecifiedExecutor();
        log.info("testUseSpecifiedExecutor done");
    }
}
