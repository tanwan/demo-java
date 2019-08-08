/*
 * Created by lzy on 2019-08-07 14:13.
 */
package com.lzy.demo.spring.mvc;

import com.lzy.demo.spring.mvc.application.MVCApplication;
import com.lzy.demo.spring.mvc.config.GlobalExceptionHandler;
import com.lzy.demo.spring.mvc.controller.GlobalExceptionController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 全局异常捕获测试
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest(classes = MVCApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@SpringJUnitConfig(classes = {GlobalExceptionController.class, GlobalExceptionHandler.class})
public class GlobalExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;

    /**
     * 测试运行时异常
     *
     * @throws Exception the exception
     * @see GlobalExceptionHandler
     */
    @Test
    public void testRuntimeException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/global/runtime-exception"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(MockMvcResultMatchers.content().string("global handle runtime exception"))
                .andReturn();
    }

    /**
     * 测试运行时异常
     *
     * @throws Exception the exception
     * @see GlobalExceptionHandler
     */
    @Test
    public void testException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/global/exception"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("global handle exception"))
                .andReturn();
    }
}
