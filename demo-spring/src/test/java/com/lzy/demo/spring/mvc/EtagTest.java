package com.lzy.demo.spring.mvc;

import com.lzy.demo.spring.mvc.application.MVCApplication;
import com.lzy.demo.spring.mvc.config.FilterConfig;
import com.lzy.demo.spring.mvc.controller.SimpleRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * etag配合If-None-Match可以用来减少带宽
 */
@SpringBootTest(classes = {MVCApplication.class, SimpleRestController.class, FilterConfig.ShallowEtagHeaderFilterConfig.class})
@AutoConfigureMockMvc
public class EtagTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 测试etag
     *
     * @throws Exception e
     */
    @Test
    public void testEtag() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/rest/path-variable/lzy"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("lzy"))
                .andReturn();

        String etag = result.getResponse().getHeader("ETag");

        // 请求头带了If-None-Match, 则内容相同时,返回304
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/path-variable/lzy")
                        .header(HttpHeaders.IF_NONE_MATCH, etag))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_MODIFIED.value()))
                .andReturn();
    }
}
