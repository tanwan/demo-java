package com.lzy.demo.spring.mvc;

import com.lzy.demo.spring.mvc.application.MVCApplication;
import com.lzy.demo.spring.mvc.bean.Message;
import com.lzy.demo.spring.mvc.controller.SimpleRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestMethodArgumentResolver;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Map;

/**
 * REST测试
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest(classes = {MVCApplication.class, SimpleRestController.class})
@AutoConfigureMockMvc
public class SimpleRestTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 测试url参数
     *
     * @throws Exception the exception
     * @see SimpleRestController#pathVariable(String)
     */
    @Test
    public void testPathVariable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/path-variable/lzy"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("lzy"))
                .andReturn();
    }

    /**
     * 测试请求参数
     *
     * @throws Exception the exception
     * @see SimpleRestController#requestParam(Map)
     */
    @Test
    public void testRequestParam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/request-param?urlParam=urlParam"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.urlParam").value("urlParam"))
                .andReturn();
    }

    /**
     * 测试@RequestBody,@ResponseBody
     *
     * @throws Exception the exception
     * @see SimpleRestController#body(Map)
     */
    @Test
    public void testBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/body")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"param\":1,\"haha\":2}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.param").value("1"))
                .andReturn();
    }

    /**
     * 测试header
     *
     * @throws Exception the exception
     * @see SimpleRestController#header(Map)
     */
    @Test
    public void testHeader() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/header")
                        .header("headerName", "headerValue"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.header().exists("header"))
                .andReturn();
    }


    /**
     * 测试cookie
     *
     * @throws Exception the exception
     * @see SimpleRestController#cookie(String, HttpServletResponse)
     */
    @Test
    public void testCookie() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/cookie")
                        .cookie(new Cookie("cookie", "cookieValue")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.cookie().value("cookie", URLEncoder.encode("hello world", StandardCharsets.UTF_8.name())))
                .andReturn();
    }

    /**
     * 测试@RequestAttribute
     *
     * @throws Exception the exception
     * @see SimpleRestController#preRequestAttribute
     * @see SimpleRestController#requestAttribute
     */
    @Test
    public void testRequestAttribute() throws Exception {
        // mockMvc并不会真正的forward到相应的url
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/pre-request-attribute"))
                .andDo(MockMvcResultHandlers.print())
                // 判断forward的url
                .andExpect(MockMvcResultMatchers.forwardedUrl("/rest/request-attribute"))
                // 判断设置的attribute
                .andExpect(MockMvcResultMatchers.request().attribute("attribute", "hello world"))
                .andReturn();
        // 因为不能真正的forward,所以这边就设置了attribute,然后再请求
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/request-attribute")
                        // 设置attribute
                        .requestAttr("attribute", "hello world"))
                .andExpect(MockMvcResultMatchers.content().string("hello world"))
                .andReturn();
    }

    /**
     * 测试Model
     *
     * @throws Exception the exception
     * @see SimpleRestController#model(Message)
     */
    @Test
    public void testModel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/model")
                        .param("code", "1")
                        // 如果请求类型有inner属性,并且inner属性的类型有property字段,则可以为其赋值
                        .param("inner.property", "value"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 测试HttpEntity
     *
     * @throws Exception the exception
     * @see SimpleRestController#httpEntity(RequestEntity)
     */
    @Test
    public void testHttpEntity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/http-entity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"param\":1,\"haha\":2}"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 测试principal
     *
     * @throws Exception the exception
     * @see SimpleRestController#principal(Principal)
     */
    @Test
    public void testPrincipal() throws Exception {
        // 需要配合spring security
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/principal"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 测试获取方法
     *
     * @throws Exception the exception
     * @see SimpleRestController#method(HttpMethod)
     */
    @Test
    public void testMethod() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/method"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("GET"))
                .andReturn();
    }

    /**
     * 测试stream
     *
     * @throws Exception the exception
     * @see SimpleRestController#stream(InputStream, OutputStream)
     */
    @Test
    public void testStream() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/stream")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"param\":1}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("{\"param\":1}"))
                .andReturn();
    }


    /**
     * 测试国际化
     *
     * @throws Exception the exception
     * @see SimpleRestController#locale(Locale)
     * @see ServletRequestMethodArgumentResolver#resolveArgument(java.lang.Class, jakarta.servlet.http.HttpServletRequest)
     */
    @Test
    public void testLocale() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/locale"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string(Locale.getDefault().getLanguage()))
                .andReturn();
        // header指定国际化
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/locale")
                        // 注意这边要使用-,不能使用下划线
                        .header("Accept-Language", "zh-CN"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string(Locale.SIMPLIFIED_CHINESE.toString()))
                .andReturn();
    }

    /**
     * 测试ZoneId
     *
     * @throws Exception the exception
     * @see SimpleRestController#zoneId(ZoneId)
     * @see ServletRequestMethodArgumentResolver#resolveArgument(java.lang.Class, jakarta.servlet.http.HttpServletRequest)
     */
    @Test
    public void testZoneId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/zone-id"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string(ZoneId.systemDefault().toString()))
                .andReturn();
    }

    /**
     * 测试异常
     *
     * @throws Exception the exception
     * @see SimpleRestController#exception()
     * @see SimpleRestController#handleException(Exception)
     */
    @Test
    public void testException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/exception"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("handle exception"))
                .andReturn();
    }


    @SpringBootTest(classes = {MVCApplication.class, SimpleRestController.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
    public static class ApplicationTest {
        /**
         * 启动应用
         *
         * @throws Exception the exception
         */
        @Test
        public void startApplication() throws Exception {
            Thread.sleep(1000000);
        }
    }

}
