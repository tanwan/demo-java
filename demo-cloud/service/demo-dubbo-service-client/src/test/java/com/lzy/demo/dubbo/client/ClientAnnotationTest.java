package com.lzy.demo.dubbo.client;

import com.lzy.demo.dubbo.api.SimpleService;
import com.lzy.demo.dubbo.message.SimpleRequest;
import org.apache.dubbo.config.annotation.DubboReference;
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
@SpringJUnitConfig({ClientAnnotationTest.class})
@PropertySource("classpath:/client.properties")
@EnableDubbo(scanBasePackages = "com.lzy.demo.dubbo.client")
public class ClientAnnotationTest {


    /**
     * 注解的属性同dubbo:reference
     */
    @DubboReference(validation = "true")
    private SimpleService simpleService;

    /**
     * 测试客户端
     */
    @Test
    public void testClient() {
        SimpleRequest simpleRequest = new SimpleRequest();
        simpleRequest.setRequest("request");
        System.out.println(simpleService.simpleService(simpleRequest));
    }
}
