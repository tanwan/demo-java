package com.lzy.demo.io.http;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebClientTest {

    private static WebClient webClient;

    private static final Integer PORT = 19003;

    private static final String HOST = "http://127.0.0.1:" + PORT;

    private static ServerApplication serverApplication = new ServerApplication(PORT);

    @BeforeAll
    public static void startApplication() throws InterruptedException {
        serverApplication.startServer();
        // 等待server启动
        Thread.sleep(500);

        webClient = createClient();
    }

    @AfterAll
    public static void stopApplication() {
        serverApplication.stop();
    }

    private static WebClient createClient() {
        // 自定义
//        WebClient.builder()
//                .defaultHeader()
//                .filters()
//                .build();

        // 默认的client
        return WebClient.create(HOST);
    }


    /**
     * 测试get
     */
    @Test
    public void testGet() {
        Map result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/rest/get/path")
                        .queryParam("queryParam", "queryParam value")
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                // Mono请求block的时候,才会发送请求
                .block();

        System.out.println(result);
        assertEquals("queryParam value", result.get("queryParam"));
    }

    /**
     * 测试post
     */
    @Test
    public void testPost() {
        Map result = webClient.post()
                .uri("/rest/post")
                .bodyValue(Map.of("key", "value"))
                .retrieve()
                .bodyToMono(Map.class)
                // Mono请求block的时候,才会发送请求
                .block();

        System.out.println(result);
        assertThat(result.toString()).contains("value");
    }


    /**
     * 测试put
     */
    @Test
    public void testPut() {
        Map result = webClient.put()
                .uri("/rest/put/1")
                .bodyValue(Map.of("key", "value"))
                .retrieve()
                .bodyToMono(Map.class)
                // Mono请求block的时候,才会发送请求
                .block();

        System.out.println(result);
        assertThat(result.toString()).contains("value");
    }


    /**
     * 测试delete
     */
    @Test
    public void testDelete() {
        Map result = webClient.delete()
                .uri("/rest/delete/1")
                .retrieve()
                .bodyToMono(Map.class)
                // Mono请求block的时候,才会发送请求
                .block();

        System.out.println(result);
        assertEquals(1, result.get("id"));
    }

    /**
     * 测试form-data urlencoded, 一般的表单
     */
    @Test
    public void testFormUrlencoded() {
        Map result = webClient.post()
                .uri("/rest/form-data")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                // BodyInserters还有多种方法
                .body(BodyInserters.fromFormData("formField", "form field value").with("k2", "v2"))
                .retrieve()
                .bodyToMono(Map.class)
                // Mono请求block的时候,才会发送请求
                .block();

        System.out.println(result);
        assertThat(result.toString()).contains("form field value");
    }

    /**
     * 测试form-data, 带有文件上传的表单
     */
    @Test
    public void testFormData() {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        // 文件
        //builder.part("file", new FileSystemResource(""));
        builder.part("formField", "form field value");

        Map result = webClient.post()
                .uri("/rest/form-data")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                // BodyInserters.fromResource(): 直接上传文件
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(Map.class)
                // Mono请求block的时候,才会发送请求
                .block();

        System.out.println(result);
        assertThat(result.toString()).contains("form field value");
    }

    /**
     * 测试header
     */
    @Test
    public void testHeader() {
        Mono<ResponseEntity<Map>> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/rest/header")
                        .build())
                .header("headerKey", "header value")
                .retrieve()
                .toEntity(Map.class);

        ResponseEntity<Map> entity = response.block();

        System.out.println(entity.getBody());
        assertEquals("header value", entity.getBody().get("headerKey"));
        assertEquals("headerKey override", entity.getHeaders().getFirst("headerKey"));
    }

    /**
     * 测试cookie
     */
    @Test
    public void testCookie() {
        String requestCookie = URLEncoder.encode("cookie value", StandardCharsets.UTF_8);
        String responseCookie = URLEncoder.encode("add cookie value", StandardCharsets.UTF_8);

        MultiValueMap<String, ResponseCookie> cookies = new LinkedMultiValueMap<>();

        Map result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/rest/cookie")
                        .build())
                .cookie("cookieKey", requestCookie)
                .exchangeToMono(response -> {
                    cookies.putAll(response.cookies());
                    return response.bodyToMono(Map.class);
                })
                // Mono请求block的时候,才会发送请求
                .block();


        System.out.println(result);
        assertEquals(requestCookie, result.get("cookieKey"));
        assertEquals(responseCookie, cookies.getFirst("addCookie").getValue());
    }


    /**
     * 代理
     */
    @Test
    public void testProxy() {
        HttpClient httpClient = HttpClient.create()
                .proxy(proxy -> proxy
                        .type(ProxyProvider.Proxy.HTTP)
                        .host("127.0.0.1")
                        .port(9090));

        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(HOST)
                .build();

        Map result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/rest/get/path")
                        .queryParam("queryParam", "queryParam value")
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                // Mono请求block的时候,才会发送请求
                .block();

        System.out.println(result);
    }
}
