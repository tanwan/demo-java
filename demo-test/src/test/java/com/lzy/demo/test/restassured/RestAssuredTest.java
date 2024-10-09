package com.lzy.demo.test.restassured;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * rest-assured的使用
 * reset-assured发送的是真实的请求
 *
 * @author lzy
 * @version v1.0
 */
public class RestAssuredTest {

    private static final Integer PORT = 19001;

    private static final ServerApplication serverApplication = new ServerApplication(PORT);

    @BeforeAll
    public static void startApplication() throws InterruptedException {
        serverApplication.startServer();
        // 等待server启动
        Thread.sleep(500);
    }

    @AfterAll
    public static void stopApplication() {
        serverApplication.stop();
    }

    /**
     * 基础用法
     */
    @Test
    public void testBase() {
        Response response = given()
                .port(PORT)
                .contentType(ContentType.JSON)
                // query参数
                .queryParam("queryParam", "queryParam value")
                // body
                .body(Map.of("key", "value"))
                .when()
                // path也可以使用{}作用插值
                .post("/rest/post/{pathParam}", "path")
                .then()
                .statusCode(200)
                // 判断json
                .body("body.key", equalTo("value"))
                // 判断string
                .body(containsString("pathParam"))
                // extract 可以返回response
                .extract().response();

        // response可以获取到body cookie header
        System.out.println(response.getBody().asString());
        System.out.println(response.getHeaders());
        System.out.println(response.getCookies());
    }


    /**
     * 测试json
     */
    @Test
    public void testJson() {
        given()
                .port(PORT)
                .contentType(ContentType.JSON)
                .body(Map.of("key", "value",
                        "list", List.of(1, 2, 3),
                        "listMap", List.of(Map.of("k1", "v1"), Map.of("k2", "v2"))))
                .when()
                .post("/rest/post/path")
                .then()
                .statusCode(200)
                .body("body.key", equalTo("value"))
                .body("body.list", contains(1, 2, 3))
                .body("body.list[0]", equalTo(1))
                .body("body.listMap[0]", equalTo(Map.of("k1", "v1")));
    }

    /**
     * 测试cookie和header
     */
    @Test
    public void testCookieAndHeader() {
        given()
                .port(PORT)
                .contentType(ContentType.JSON)
                .header(ServerApplication.TEST_HEADER, "header value")
                // cookie有空格需要进行编码
                .cookie(ServerApplication.TEST_COOKIE, URLEncoder.encode("cookie value", StandardCharsets.UTF_8))
                .when()
                .post("/rest/post/path")
                .then()
                .statusCode(200)
                // 判断header
                .header(ServerApplication.TEST_HEADER, "header value")
                // cookie的其它参数可以使用cookie(cookieName, RestAssuredMatchers.detailedCookie())
                .cookie(ServerApplication.TEST_COOKIE, URLEncoder.encode("cookie value", StandardCharsets.UTF_8));
    }

    /**
     * 测试form-data
     */
    @Test
    public void testFormData() {
        given()
                .port(PORT)
                .contentType(ContentType.URLENC)
                .formParams(Map.of("key", "value"))
                .when()
                .post("/rest/form-data")
                .then()
                .statusCode(200)
                .body("allField[0].key", equalTo("value"));
    }
}
