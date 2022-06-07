package com.lzy.demo.service;

import com.lzy.demo.service.service.SimpleFeignService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class FeignTest {

    /**
     * 从demo-service-client获取端口
     *
     * @param simpleFeignService the simple serivce
     */
    @Test
    public void serverPort(@Autowired SimpleFeignService simpleFeignService) {
        Integer port = simpleFeignService.serverPort();
        System.out.println(port);
        Assertions.assertThat(port)
                .isIn(28010, 28011);
    }

    /**
     * 测试get
     *
     * @param simpleFeignService the simple service
     */
    @Test
    public void testGet(@Autowired SimpleFeignService simpleFeignService) {
        Assertions.assertThat(simpleFeignService.getRequest("requestParam", "get"))
                .containsEntry("pathVariable", "get").containsEntry("requestParam", "requestParam");

        Map<String, Object> request = new HashMap<>(1);
        request.put("requestParam", "requestParam");
        Assertions.assertThat(simpleFeignService.getRequest(request, "get"))
                .containsEntry("pathVariable", "get").containsEntry("requestParam", "requestParam");
    }


    /**
     * 测试post
     *
     * @param simpleFeignService the simple service
     */
    @Test
    public void getPost(@Autowired SimpleFeignService simpleFeignService) {
        Map<String, Object> request = new HashMap<>(1);
        request.put("request", "request");
        Assertions.assertThat(simpleFeignService.postRequest(request, "post"))
                .containsEntry("pathVariable", "post").containsEntry("request", "request");
    }

    /**
     * 测试put
     *
     * @param simpleFeignService the simple service
     */
    @Test
    public void testPut(@Autowired SimpleFeignService simpleFeignService) {
        Map<String, Object> request = new HashMap<>(1);
        request.put("request", "request");
        Assertions.assertThat(simpleFeignService.putRequest(request, "put"))
                .containsEntry("pathVariable", "put").containsEntry("request", "request");
    }

    /**
     * 测试delete
     *
     * @param simpleFeignService the simple service
     */
    @Test
    public void testDelete(@Autowired SimpleFeignService simpleFeignService) {
        Map<String, Object> request = new HashMap<>(1);
        request.put("request", "request");
        Assertions.assertThat(simpleFeignService.deleteRequest(request, "delete"))
                .containsEntry("pathVariable", "delete").containsEntry("request", "request");
    }
}
