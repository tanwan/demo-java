package com.lzy.demo.spring.mvc;

import com.lzy.demo.spring.mvc.application.MVCApplication;
import com.lzy.demo.spring.mvc.config.FilterConfig;
import com.lzy.demo.spring.mvc.controller.SimpleRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
public class FilterTest {

    /**
     * FilterRegistrationBean测试
     *
     * @see ServletContextInitializerBeans#addServletContextInitializerBeans(org.springframework.beans.factory.ListableBeanFactory)
     */
    @WebMvcTest
    @SpringJUnitConfig({SimpleRestController.class, FilterConfig.FilterRegistrationBeanConfig.class})
    public static class FilterRegistrationBeanTest {

        @Autowired
        protected MockMvc mockMvc;

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
    @SpringBootTest(classes = {MVCApplication.class, SimpleRestController.class, FilterConfig.ServletContextInit.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
    public static class ApplicationTest {
        /**
         * 启动应用
         * 测试Rest,需要使用postman测试
         * 测试界面,需要使用postman测试
         *
         * @throws Exception the exception
         */
        @Test
        public void startApplication() throws Exception {
            Thread.sleep(1000000);
        }
    }
}
