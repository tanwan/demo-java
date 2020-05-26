/*
 * Created by lzy on 2020/5/26 3:09 PM.
 */
package com.lzy.demo.spring.mvc;

import com.lzy.demo.spring.mvc.application.MVCApplication;
import com.lzy.demo.spring.mvc.config.GlobalExceptionHandler;
import com.lzy.demo.spring.mvc.controller.ValidateController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

/**
 * The type Validate test.
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest(classes = MVCApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@SpringJUnitConfig(classes = {ValidateController.class, GlobalExceptionHandler.class})
public class ValidateTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test not empty.
     *
     * @throws Exception the exception
     */
    @Test
    public void testNotEmpty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/validate/not-empty")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
