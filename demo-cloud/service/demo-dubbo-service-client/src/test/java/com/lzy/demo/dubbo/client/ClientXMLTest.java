package com.lzy.demo.dubbo.client;

import com.lzy.demo.dubbo.api.SimpleService;
import com.lzy.demo.dubbo.message.SimpleRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.validation.ValidationException;

import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * 配置xml配置dubbo
 *
 * @author lzy
 * @version v1.0
 */
@ImportResource("classpath:client.xml")
@SpringJUnitConfig(ClientXMLTest.class)
public class ClientXMLTest {


    /**
     * 测试客户端
     *
     * @param simpleService the simple service
     */
    @Test
    public void testClient(@Autowired SimpleService simpleService) {
        SimpleRequest simpleRequest = new SimpleRequest();
        simpleRequest.setRequest("request");
        System.out.println(simpleService.simpleService(simpleRequest));
    }

    /**
     * 测试校验
     *
     * @param simpleService the simple service
     */
    @Test
    public void testValidation(@Autowired SimpleService simpleService) {
        assertThatCode(() -> simpleService.simpleService(new SimpleRequest())).isInstanceOf(ValidationException.class);
    }
}
