package com.lzy.demo.dubbo.server;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * 使用注解配置dubbo,这边也可以跟xml配置配合,这样就不用使用properties配置
 *
 * @author lzy
 * @version v1.0
 */
@SpringJUnitConfig({ServerAnnotationTest.class})
@EnableDubbo(scanBasePackages = "com.lzy.demo.dubbo.server")
@PropertySource("classpath:/server.properties")
public class ServerAnnotationTest {


    /**
     * 测试服务端
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testServer() throws InterruptedException {
        System.out.println("dubbo server start");
        Thread.sleep(1000000);
    }
}
