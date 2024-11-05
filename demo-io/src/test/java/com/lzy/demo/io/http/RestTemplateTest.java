package com.lzy.demo.io.http;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestTemplateTest {

    private static RestTemplate restTemplate;

    private static final Integer PORT = 19004;

    private static final String HOST = "http://127.0.0.1:" + PORT;

    private static ServerApplication serverApplication = new ServerApplication(PORT);

    @BeforeAll
    public static void startApplication() throws InterruptedException {
        serverApplication.startServer();
        // 等待server启动
        Thread.sleep(500);

        restTemplate = createClient();
    }

    @AfterAll
    public static void stopApplication() {
        serverApplication.stop();
    }

    private static RestTemplate createClient() {
        restTemplate = new RestTemplate();
        return restTemplate;
    }

    /**
     * 测试get
     */
    @Test
    public void testGet() {
        Map<String, Object> result = restTemplate.getForObject(HOST + "/rest/get/path?queryParam={queryParam}",
                Map.class, Map.of("queryParam", "queryParam value"));

        System.out.println(result);
        assertEquals("queryParam value", result.get("queryParam"));
    }

    /**
     * 测试post
     */
    @Test
    public void testPost() {
        Map<String, Object> result = restTemplate.postForObject(HOST + "/rest/post",
                Map.of("key", "value"),
                Map.class);

        System.out.println(result);
        assertThat(result.toString()).contains("value");
    }

    /**
     * 测试put
     */
    @Test
    public void testPut() {
        // put是拿不到响应的
        restTemplate.put(HOST + "/rest/put/1", Map.of("key", "value"));

        // 要拿到响应需要使用exchange
        ResponseEntity<Map> entity = restTemplate.exchange(HOST + "/rest/put/1", HttpMethod.PUT, new HttpEntity<>(Map.of("key", "value")), Map.class);

        System.out.println(entity.getBody());
        assertThat(entity.getBody().toString()).contains("value");
    }

    /**
     * 测试delete
     */
    @Test
    public void testDelete() {
        // delete是拿不到响应的
        restTemplate.delete(HOST + "/rest/delete/1", Map.of("key", "value"));

        // 要拿到响应需要使用exchange
        ResponseEntity<Map> entity = restTemplate.exchange(HOST + "/rest/delete/1", HttpMethod.DELETE, HttpEntity.EMPTY, Map.class);

        System.out.println(entity.getBody());
        assertThat(entity.getBody().toString()).contains("1");
    }

    /**
     * 测试form-data urlencoded, 一般的表单
     */
    @Test
    public void testFormUrlencoded() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("formField", "form field value");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        Map<String, Object> result = restTemplate.postForObject(HOST + "/rest/form-data", request, Map.class);
        System.out.println(result);
        assertThat(result.toString()).contains("form field value");
    }


    /**
     * 测试form-data, 带有文件上传的表单
     */
    @Test
    public void testFormData() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        //body.add("file", file);
        body.add("formField", "form field value");
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        Map<String, Object> result = restTemplate.postForObject(HOST + "/rest/form-data", request, Map.class);
        System.out.println(result);
        assertThat(result.toString()).contains("form field value");
    }

    /**
     * 测试header
     */
    @Test
    public void testHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("headerKey", "header value");
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(headers);

        // getForEntity只能获取ResponseEntity, 不能传HttpEntity
        ResponseEntity<Map> response = restTemplate.exchange(HOST + "/rest/header", HttpMethod.GET, request, Map.class);

        System.out.println(response.getBody());
        assertEquals("header value", response.getBody().get("headerKey"));
        assertEquals("headerKey override", response.getHeaders().getFirst("headerKey"));
    }

    /**
     * 测试cookie
     */
    @Test
    public void testCookie() {
        String requestCookie = URLEncoder.encode("cookie value", StandardCharsets.UTF_8);
        String responseCookie = URLEncoder.encode("add cookie value", StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, "cookieKey=" + requestCookie);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(HOST + "/rest/cookie", HttpMethod.GET, request, Map.class);

        System.out.println(response.getBody());
        System.out.println(response.getHeaders().get(HttpHeaders.SET_COOKIE));
        assertEquals(requestCookie, response.getBody().get("cookieKey"));
        assertEquals("addCookie=" + responseCookie, response.getHeaders().getFirst(HttpHeaders.SET_COOKIE));
    }


    /**
     * 代理
     */
    @Test
    public void testProxy() {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 9090));
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(proxy);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        Map<String, Object> result = restTemplate.getForObject(HOST + "/rest/get/path?queryParam={queryParam}",
                Map.class, Map.of("queryParam", "queryParam value"));

        System.out.println(result);
        assertEquals("queryParam value", result.get("queryParam"));
    }
}
