package com.lzy.demo.spring.mvc;

import com.lzy.demo.spring.mvc.controller.PatternController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 测试通配符
 *
 * @author lzy
 * @version v1.0
 */
@WebMvcTest
@SpringJUnitConfig(PatternController.class)
public class PatternTest {

    @Autowired
    private MockMvc mockMvc;


    /**
     * 测试问号只匹配一个字符
     *
     * @throws Exception the exception
     */
    @Test
    public void testQuestionMark() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pattern/question-mark/ab"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("questionMark"))
                .andReturn();
        // 0个和多个字符不匹配
        mockMvc.perform(MockMvcRequestBuilders.get("/pattern/question-mark/a"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
        mockMvc.perform(MockMvcRequestBuilders.get("/pattern/question-mark/abc"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    /**
     * 测试单个星号
     *
     * @throws Exception the exception
     */
    @Test
    public void testSingleAsterisk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pattern/single-asterisk/lzy"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("singleAsterisk"))
                .andReturn();
        // 不匹配多层路径
        mockMvc.perform(MockMvcRequestBuilders.get("/pattern/single-asterisk/lzy/jong"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    /**
     * 测试2个星号
     *
     * @throws Exception the exception
     */
    @Test
    public void testDoubleAsterisk() throws Exception {
        // 匹配无路径
        mockMvc.perform(MockMvcRequestBuilders.get("/pattern/double-asterisk"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("doubleAsterisk"))
                .andReturn();

        // 匹配单个路径
        mockMvc.perform(MockMvcRequestBuilders.get("/pattern/double-asterisk/lzy"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("doubleAsterisk"))
                .andReturn();
        // 匹配多个路径
        mockMvc.perform(MockMvcRequestBuilders.get("/pattern/double-asterisk/lzy/jong"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("doubleAsterisk"))
                .andReturn();
    }

    /**
     * 测试正则表达式
     *
     * @throws Exception the exception
     */
    @Test
    public void regularExpression() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pattern/regular/spring-1.0.0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("spring1.0.0"))
                .andReturn();
    }

    /**
     * 测试地址映射
     *
     * @throws Exception the exception
     */
    @Test
    public void testSuffix() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pattern/suffix.text"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
