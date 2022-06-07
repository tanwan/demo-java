package com.lzy.demo.dubbo.server;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * 使用xml配置dubbo
 *
 * @author lzy
 * @version v1.0
 */
@ImportResource("classpath:server.xml")
@SpringJUnitConfig({SimpleServiceSpringImpl.class, ServerXMLTest.class})
public class ServerXMLTest {


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
