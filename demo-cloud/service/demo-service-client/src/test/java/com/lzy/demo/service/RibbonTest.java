package com.lzy.demo.service;

import com.lzy.demo.service.constant.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class RibbonTest {

    /**
     * 从demo-service-client获取端口
     *
     * @param restTemplate the rest template
     */
    @Test
    public void serverPort(@Autowired RestTemplate restTemplate) {
        Integer port = restTemplate.getForObject(Constants.DEMO_SERVICE_SERVER + "/port", Integer.class);
        System.out.println(port);
        assertThat(port)
                .isIn(28010, 28011);
    }

    /**
     * 测试get
     *
     * @param restTemplate the rest template
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testGet(@Autowired RestTemplate restTemplate) {
        assertThat(restTemplate.getForObject(Constants.DEMO_SERVICE_SERVER + "/get/{pathVariable}?requestParam={requestParam}", Map.class, "get", "requestParam"))
                .containsEntry("pathVariable", "get").containsEntry("requestParam", "requestParam");
    }


    /**
     * 测试post
     *
     * @param restTemplate the rest template
     */
    @Test
    @SuppressWarnings("unchecked")
    public void getPost(@Autowired RestTemplate restTemplate) {
        Map<String, Object> request = new HashMap<>(1);
        request.put("request", "request");
        assertThat(restTemplate.postForObject(Constants.DEMO_SERVICE_SERVER + "/post/{pathVariable}", request, Map.class, "post"))
                .containsEntry("pathVariable", "post").containsEntry("request", "request");
    }

    /**
     * 测试put
     *
     * @param restTemplate the rest template
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testPut(@Autowired RestTemplate restTemplate) {
        Map<String, Object> request = new HashMap<>(1);
        request.put("request", "request");
        assertThat(restTemplate.postForObject(Constants.DEMO_SERVICE_SERVER + "/post/{pathVariable}", request, Map.class, "put"))
                .containsEntry("pathVariable", "put").containsEntry("request", "request");
    }

    /**
     * 测试delete
     *
     * @param restTemplate the rest template
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testDelete(@Autowired RestTemplate restTemplate) {
        Map<String, Object> request = new HashMap<>(1);
        request.put("request", "request");
        assertThat(restTemplate.postForObject(Constants.DEMO_SERVICE_SERVER + "/post/{pathVariable}", request, Map.class, "delete"))
                .containsEntry("pathVariable", "delete").containsEntry("request", "request");
    }
}
