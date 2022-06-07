package com.lzy.demo.spring.mvc;

import com.lzy.demo.spring.mvc.controller.SessionController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 测试session
 *
 * @author lzy
 * @version v1.0
 */
@WebMvcTest
@SpringJUnitConfig(SessionController.class)
public class SessionTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 测试session
     *
     * @throws Exception the exception
     */
    @Test
    public void testHttpSession() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/session/http-session"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("init"))
                .andReturn();
        // 使用上面获取到的session
        mockMvc.perform(MockMvcRequestBuilders.get("/session/http-session")
                        .session((MockHttpSession) mvcResult.getRequest().getSession()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("hello world"))
                .andReturn();
    }

    /**
     * 测试使用HttpServletRequest 读写session
     *
     * @throws Exception the exception
     */
    @Test
    public void testHttpServletRequest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/session/http-servlet-request"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("init"))
                .andReturn();
        // 使用上面获取到的session
        mockMvc.perform(MockMvcRequestBuilders.get("/session/http-servlet-request")
                        .session((MockHttpSession) mvcResult.getRequest().getSession()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("hello world"))
                .andReturn();
    }

    /**
     * 测试使用@SessionAttribute获取session
     *
     * @throws Exception the exception
     */
    @Test
    public void testSessionAttribute() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/session/session-attribute"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("init"))
                .andReturn();
        // 使用上面获取到的session
        mockMvc.perform(MockMvcRequestBuilders.get("/session/session-attribute")
                        .session((MockHttpSession) mvcResult.getRequest().getSession()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string("hello world"))
                .andReturn();
    }
}
