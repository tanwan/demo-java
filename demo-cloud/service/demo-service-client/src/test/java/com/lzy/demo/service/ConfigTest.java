package com.lzy.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ClientApplication.class)
@DirtiesContext
@AutoConfigureMockMvc
@ActiveProfiles("spring-cloud-config-url")
public class ConfigTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Config bean.
     *
     * @throws Exception the exception
     */
    @Test
    public void configBean() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/config/bean"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

}
