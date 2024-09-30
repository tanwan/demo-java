package com.lzy.demo.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

/**
 * 默认测试端口为8081, 在application.properties使用quarkus.http.test-port可以指定
 * 目前单测需要使用gradle启动
 *
 * @author lzy
 * @version v1.0
 */
@QuarkusTest
public class RestTest {

    /**
     * url参数
     */
    @Test
    public void testPathVariable() {
        given().when().get("/rest/path-variable/lzy")
                .then()
                .statusCode(200)
                .body(is("lzy"));
    }

    /**
     * 请求参数
     */
    @Test
    public void testRequestParam() {
        given().param("param1", "value1")
                .param("param2", "3")
                .when().get("/rest/request-param")
                .then()
                .statusCode(200)
                .body("param1", is("value1"))
                .body("param2", is(3));
    }

    /**
     * body
     */
    @Test
    public void testBody() {
        given().contentType(ContentType.JSON)
                .body(Map.of("key1", "value1", "key2", "value2"))
                .when().post("/rest/body")
                .then()
                .statusCode(200)
                .body("key1", is("value1"))
                .body("key2", is("value2"));
    }

    /**
     * header
     */
    @Test
    public void testHeader() {
        given().headers("header", "header")
                .when().get("/rest/header")
                .then()
                .statusCode(200)
                .body("header", is("header"))
                .header("header", "new header");
    }

    /**
     * cookie
     */
    @Test
    public void testCookie() {
        given().cookie("cookie", "cookie")
                .when().get("/rest/cookie")
                .then()
                .statusCode(200)
                .body("cookie", is("cookie"))
                .cookie("cookie", URLEncoder.encode("new cookie", StandardCharsets.UTF_8));
    }
}
