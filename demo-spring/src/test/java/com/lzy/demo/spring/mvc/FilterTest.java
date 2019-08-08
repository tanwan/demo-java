/*
 * Created by lzy on 2019-08-07 14:47.
 */
package com.lzy.demo.spring.mvc;

import com.lzy.demo.spring.mvc.application.MVCApplication;
import com.lzy.demo.spring.mvc.config.FilterConfig;
import com.lzy.demo.spring.mvc.controller.SampleRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletContextInitializerBeans;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

/**
 * 过滤器测试
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest(classes = MVCApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@SpringJUnitConfig(classes = {SampleRestController.class})
public abstract class FilterTest {
    @Autowired
    protected MockMvc mockMvc;

    /**
     * FilterRegistrationBean测试
     *
     * @see ServletContextInitializerBeans#addServletContextInitializerBeans(org.springframework.beans.factory.ListableBeanFactory)
     */
    @SpringJUnitConfig(classes = {FilterConfig.FilterRegistrationBeanConfig.class})
    public static class FilterRegistrationBeanTest extends FilterTest {
        /**
         * 测试Rest
         *
         * @throws Exception the exception
         */
        @Test
        public void testRest() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/rest/locale"))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        }

        /**
         * 测试界面
         *
         * @throws Exception the exception
         */
        @Test
        public void testView() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/public.html"))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        }
    }

    /**
     * ServletContextInit测试
     */
    @SpringJUnitConfig(classes = {FilterConfig.ServletContextInit.class})
    public static class ServletContextInitTest extends FilterTest {
        /**
         * 测试Rest,需要使用postman测试
         *
         * @throws Exception the exception
         */
        @Test
        public void testRest() throws Exception {
            Thread.sleep(100000);
        }

        /**
         * 测试界面,需要使用postman测试
         *
         * @throws Exception the exception
         */
        @Test
        public void testView() throws Exception {
            Thread.sleep(100000);
        }
    }
}
