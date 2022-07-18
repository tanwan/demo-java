package com.lzy.demo.spring.boot.servlet;

import com.lzy.demo.spring.mvc.servlet.ServletConfig;
import com.lzy.demo.spring.mvc.servlet.ServletController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Servlet测试
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest(classes = {ServletController.class, ServletConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ServletTest {

    /**
     * 测试servlet
     * 有SpringBootTest可以自动注入TestRestTemplate
     *
     * @param testRestTemplate the test rest template
     * @see org.springframework.boot.test.web.client.TestRestTemplateContextCustomizerFactory
     */
    @Test
    public void testServlet(@Autowired TestRestTemplate testRestTemplate) {
        assertEquals("hello world", testRestTemplate.getForObject("/servlet", String.class));
    }

    /**
     * 测试过滤器
     *
     * @param mockMvc the mock mvc
     * @throws Exception the exception
     */
    @Test
    public void testFilter(@Autowired MockMvc mockMvc) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/filter"))
                .andDo(MockMvcResultHandlers.print());
    }
}
