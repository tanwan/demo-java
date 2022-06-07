package com.lzy.demo.spring.mvc;

import com.lzy.demo.spring.mvc.controller.SessionAttributesController;
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
 * 测试@SessionAttributes
 *
 * @author lzy
 * @version v1.0
 */
@WebMvcTest
@SpringJUnitConfig(SessionAttributesController.class)
public class SessionAttributesTest {


    @Autowired
    private MockMvc mockMvc;

    /**
     * 测试ModelMap
     *
     * @throws Exception the exception
     */
    @Test
    public void testModelMap() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/session-attributes/model-map"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andReturn();
        // 使用上面获取到的session
        mockMvc.perform(MockMvcRequestBuilders.get("/session-attributes/model-map")
                        .session((MockHttpSession) mvcResult.getRequest().getSession()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1))
                .andReturn();
    }

    /**
     * 测试HttpSession
     *
     * @throws Exception the exception
     */
    @Test
    public void testHttpSession() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/session-attributes/http-session"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andReturn();
        // 使用上面获取到的session
        mockMvc.perform(MockMvcRequestBuilders.get("/session-attributes/http-session")
                        .session((MockHttpSession) mvcResult.getRequest().getSession()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1))
                .andReturn();
    }

    /**
     * 测试ModelMap
     *
     * @throws Exception the exception
     */
    @Test
    public void testModelAttribute() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/session-attributes/model-map"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andReturn();
        // 使用上面获取到的session
        mockMvc.perform(MockMvcRequestBuilders.get("/session-attributes/model-attribute")
                        .session((MockHttpSession) mvcResult.getRequest().getSession()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
                .andReturn();
    }
}
