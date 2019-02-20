/*
 * Created by lzy on 2019-02-19 17:38.
 */
package com.lzy.demo.spring.ioc.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Web scope test.
 *
 * @author lzy
 * @version v1.0
 */
@AutoConfigureWebMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@SpringJUnitConfig(value = {ServletWebServerFactoryAutoConfiguration.class, WebScopeTest.WebScopeController.class, RequestScope.class, SessionScope.class})
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
                .andExpect(ResultMatcher.matchAll(MockMvcResultMatchers.jsonPath("sameInstance").value(true),
                        MockMvcResultMatchers.jsonPath("instantiationTimes").value(1)));
        mockMvc.perform(MockMvcRequestBuilders.get("/request-scope"))
                .andExpect(ResultMatcher.matchAll(MockMvcResultMatchers.jsonPath("sameInstance").value(true),
                        MockMvcResultMatchers.jsonPath("instantiationTimes").value(2)));
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
                .andExpect(ResultMatcher.matchAll(MockMvcResultMatchers.jsonPath("sameInstance").value(true),
                        MockMvcResultMatchers.jsonPath("instantiationTimes").value(1)));

        mockMvc.perform(MockMvcRequestBuilders.get("/session-scope").session(session))
                .andExpect(ResultMatcher.matchAll(MockMvcResultMatchers.jsonPath("sameInstance").value(true),
                        MockMvcResultMatchers.jsonPath("instantiationTimes").value(1)));
    }


    /**
     * The type Web scope controller.
     */
    @RestController
    public static class WebScopeController {
        @Resource
        private ApplicationContext applicationContext;


        @GetMapping("/request-scope")
        public Map<String, Object> requestScope() {
            RequestScope requestScope1 = applicationContext.getBean("requestScope", RequestScope.class);
            RequestScope requestScope2 = applicationContext.getBean("requestScope", RequestScope.class);
            return handleResult(requestScope1 == requestScope2, requestScope1.getInstantiationTimes());
        }

        /**
         * Test filter string.
         *
         * @return the string
         */
        @GetMapping("/session-scope")
        public Map<String, Object> sessionScope() {
            SessionScope sessionScope1 = applicationContext.getBean("sessionScope", SessionScope.class);
            SessionScope sessionScope2 = applicationContext.getBean("sessionScope", SessionScope.class);
            return handleResult(sessionScope1 == sessionScope2, sessionScope1.getInstantiationTimes());
        }

        private Map<String, Object> handleResult(boolean same, Integer instantiationTimes) {
            Map<String, Object> result = new HashMap<>();
            result.put("sameInstance", same);
            result.put("instantiationTimes", instantiationTimes);
            return result;
        }
    }


}
