/*
 * Created by lzy on 2019-08-02 08:50.
 */
package com.lzy.demo.spring.mvc;

import com.lzy.demo.spring.mvc.application.MVCApplication;
import com.lzy.demo.spring.mvc.controller.SimpleRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestMethodArgumentResolver;

import javax.servlet.http.Cookie;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.Locale;

/**
 * REST测试
 *
 * @author lzy
 * @version v1.0
 */

@SpringBootTest(classes = MVCApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.servlet.multipart.max-file-size=100MB"})
@SpringJUnitConfig(classes = SimpleRestController.class)
public class SimpleRestTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 测试url参数
     *
     * @throws Exception the exception
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
     */
    @Test
    public void testRequestAttribute() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/pre-request-attribute"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.forwardedUrl("/rest/request-attribute"))
                .andReturn();
        // 需要手动使用postman请求/rest/pre-request-attribute来判断
        Thread.sleep(100000);
    }

    /**
     * 测试Model
     *
     * @throws Exception the exception
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
     * @see ServletRequestMethodArgumentResolver#resolveArgument(java.lang.Class, javax.servlet.http.HttpServletRequest)
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
     * @see ServletRequestMethodArgumentResolver#resolveArgument(java.lang.Class, javax.servlet.http.HttpServletRequest)
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
     */
    @Test
    public void testException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/exception"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("handle exception"))
                .andReturn();
    }
}
