package com.lzy.demo.spring.mvc;

import com.lzy.demo.spring.mvc.config.WebMvcConfig;
import com.lzy.demo.spring.mvc.controller.AsyncController;
import com.lzy.demo.spring.mvc.interceptor.MvcInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

/**
 * 拦截器测试
 *
 * @author lzy
 * @version v1.0
 */
@WebMvcTest
@SpringJUnitConfig({AsyncController.class, WebMvcConfig.class, MvcInterceptor.class})
public class InterceptorTest {


    @Autowired
    private MockMvc mockMvc;

    /**
     * 测试同步的拦截
     *
     * @throws Exception the exception
     */
    @Test
    public void testSyncInterceptor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/async/sync"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 测试异步的拦截
     *
     * @throws Exception the exception
     */
    @Test
    public void testAsyncInterceptor() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/async/callable"))
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(result))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
