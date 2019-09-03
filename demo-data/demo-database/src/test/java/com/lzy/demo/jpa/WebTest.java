/*
 * Created by lzy on 2019/9/3 10:39 AM.
 */
package com.lzy.demo.jpa;

import com.lzy.demo.jpa.application.JpaApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

/**
 * 测试EnableSpringDataWebSupport
 *
 * @author lzy
 * @version v1.0
 */
@AutoConfigureMockMvc
@SpringBootTest(classes = JpaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(properties = "spring.config.location=classpath:jpa/jpa-hakari.yml")
public class WebTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 测试FindOne
     *
     * @throws Exception the exception
     */
    @Test
    public void testFindOne() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jpa/find/1"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 测试Pageable
     *
     * @throws Exception the exception
     */
    @Test
    public void testPageable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jpa/pageable")
                .param("page", "1")
                .param("size", "30")
                .param("sort", "firstName")
                .param("sort", "lastName,desc"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    /**
     * 测试Pageable
     *
     * @throws Exception the exception
     */
    @Test
    public void testPageables() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jpa/pageables")
                .param("pageable1_page", "1")
                .param("pageable1_size", "30")
                .param("pageable1_sort", "firstName")
                .param("pageable1_sort", "lastName,desc")
                .param("pageable2_page", "0")
                .param("pageable2_size", "20"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
