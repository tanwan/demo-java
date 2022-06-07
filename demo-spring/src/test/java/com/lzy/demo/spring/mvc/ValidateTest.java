package com.lzy.demo.spring.mvc;

import com.lzy.demo.spring.mvc.config.GlobalExceptionHandler;
import com.lzy.demo.spring.mvc.controller.ValidateController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
@SpringJUnitConfig({ValidateController.class, GlobalExceptionHandler.class})
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
                // Controller下面的ExceptionHandler优先级比较高
                .andExpect(MockMvcResultMatchers.content().string("handleValidationException"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
