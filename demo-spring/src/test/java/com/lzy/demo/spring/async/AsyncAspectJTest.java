/*
 * Created by lzy on 2019-05-29 09:52.
 */
package com.lzy.demo.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * 测试使用Aspectj代理异步
 *
 * @author lzy
 * @version v1.0
 */
@EnableAsync(mode = AdviceMode.ASPECTJ)
@EnableLoadTimeWeaving
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ExtendWith(SpringExtension.class)
// 必需有自定义的配置(实现AsyncConfigurer）
@SpringJUnitConfig({AsyncBean.class, CustomAsyncConfig.class})
@Slf4j
public class AsyncAspectJTest {

    /**
     * 测试没有返回值
     * 启动的时候,虚拟机参数需要添加-ea
     * -javaagent:{{path}}/aspectjweaver-1.9.2.jar -javaagent:{{path}}/spring-instrument-5.1.6.RELEASE.jar
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
}
