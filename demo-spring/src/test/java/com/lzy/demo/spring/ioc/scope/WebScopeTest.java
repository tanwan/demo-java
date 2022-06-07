package com.lzy.demo.spring.ioc.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
@SpringJUnitConfig({WebScopeController.class, RequestScope.class, SessionScope.class})
public class WebScopeTest {

    /**
     * 测试 request scope
     *
     * @param mockMvc the mock mvc
     * @throws Exception the exception
     */
    @Test
    public void testRequestScope(@Autowired MockMvc mockMvc) throws Exception {
        //每次请求都会创建一次实例
        mockMvc.perform(MockMvcRequestBuilders.get("/request-scope"))
                .andExpectAll(MockMvcResultMatchers.jsonPath("sameInstance").value(true),
                        MockMvcResultMatchers.jsonPath("instantiationTimes").value(1));
        mockMvc.perform(MockMvcRequestBuilders.get("/request-scope"))
                .andExpectAll(MockMvcResultMatchers.jsonPath("sameInstance").value(true),
                        MockMvcResultMatchers.jsonPath("instantiationTimes").value(2));
    }

    /**
     * 测试 session scope
     *
     * @param mockMvc the mock mvc
     * @throws Exception the exception
     */
    @Test
    public void testSessionScope(@Autowired MockMvc mockMvc) throws Exception {
        MockHttpSession session = new MockHttpSession();
        //相同session只会创建1次实例
        mockMvc.perform(MockMvcRequestBuilders.get("/session-scope").session(session))
                .andExpectAll(MockMvcResultMatchers.jsonPath("sameInstance").value(true),
                        MockMvcResultMatchers.jsonPath("instantiationTimes").value(1));

        mockMvc.perform(MockMvcRequestBuilders.get("/session-scope").session(session))
                .andExpectAll(MockMvcResultMatchers.jsonPath("sameInstance").value(true),
                        MockMvcResultMatchers.jsonPath("instantiationTimes").value(1));
    }
}
