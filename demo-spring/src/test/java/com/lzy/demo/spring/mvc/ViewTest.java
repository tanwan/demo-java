package com.lzy.demo.spring.mvc;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.lzy.demo.spring.mvc.application.MVCApplication;
import com.lzy.demo.spring.mvc.controller.ViewController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewTest {
    /**
     * 开启thymeleaf
     */
    @WebMvcTest
    @TestPropertySource(properties = "spring.thymeleaf.enabled=true")
    @SpringJUnitConfig(classes = ViewController.class)
    public static class EnableThymeleafTest {
        @Autowired
        private MockMvc mockMvc;

        /**
         * 测试ModelMap
         *
         * @throws Exception exception
         * @see ViewController#modelMap(Map, ModelMap, Model)
         */
        @Test
        public void testModelMap() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/view/model-map"))
                    .andExpect(MockMvcResultMatchers.model().attribute("map", "1"))
                    .andExpect(MockMvcResultMatchers.model().attribute("modelMap", "2"))
                    .andExpect(MockMvcResultMatchers.model().attribute("model", "3"))
                    .andExpect(MockMvcResultMatchers.view().name("modelMap"));
        }

        /**
         * 测试使用模板,要返回非模板的页面,必需使用redirect或者forward
         *
         * @throws Exception exception
         * @see ViewController#publicViewWithTemplate()
         */
        @Test
        public void testPublicViewWithoutTemplate() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/view/public-view-with-template"))
                    // 判断forward的url
                    .andExpect(MockMvcResultMatchers.forwardedUrl("/public.html"))
                    .andReturn();
        }

        /**
         * 测试如果使用模板,要返回非模板的页面,必需使用redirect或者forward
         *
         * @throws Exception exception
         * @see ViewController#publicModelAndViewWithTemplate()
         */
        @Test
        public void testPublicModelAndViewWithTemplate() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/view/public-model-and-view-with-template"))
                    // 判断forward的url
                    .andExpect(MockMvcResultMatchers.forwardedUrl("/public.html"))
                    .andReturn();
        }


        /**
         * 测试使用String进行重定向,禁用模板的情况下,也适用
         *
         * @param webClient webClient
         * @throws IOException exception
         * @see ViewController#redirect()
         */
        @Test
        public void testRedirect(@Autowired WebClient webClient) throws IOException {
            HtmlPage page = webClient.getPage("/view/redirect");
            assertThat(page.getVisibleText()).contains("public");
        }


        /**
         * 测试返回ModelAndView
         *
         * @throws Exception exception
         * @see ViewController#modelAndView
         */
        @Test
        public void testModelAndView() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/view/model-and-view"))
                    .andExpect(MockMvcResultMatchers.model().attribute("map", "1"))
                    .andExpect(MockMvcResultMatchers.view().name("modelMap"));
        }

        /**
         * 测试使用string返回model
         *
         * @throws Exception exception
         * @see ViewController#stringView
         */
        @Test
        public void testStringView() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/view/string-view"))
                    .andExpect(MockMvcResultMatchers.model().attribute("map", "1"))
                    .andExpect(MockMvcResultMatchers.view().name("modelMap"));
        }
    }


    /**
     * 禁用thymeleaf
     */
    @WebMvcTest
    @TestPropertySource(properties = "spring.thymeleaf.enabled=false")
    @SpringJUnitConfig(classes = ViewController.class)
    public static class DisableThymeleafTest {
        @Autowired
        private WebClient webClient;

        @Autowired
        private MockMvc mockMvc;

        /**
         * 测试不使用模板的情况下,不使用redirect和forward就可以返回页面
         *
         * @throws Exception exception
         * @see ViewController#publicViewWithoutTemplate()
         */
        @Test
        public void testPublicViewWithoutTemplate() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/view/public-view-without-template"))
                    // 判断forward的url
                    .andExpect(MockMvcResultMatchers.forwardedUrl("/public.html"))
                    .andReturn();
        }

        /**
         * 测试使用RedirectView进行重定向,使用模板的情况下,也适用
         *
         * @throws IOException exception
         * @see ViewController#redirectView()
         */
        @Test
        public void testRedirectView() throws IOException {
            HtmlPage page = webClient.getPage("/view/redirect-view");
            assertThat(page.getVisibleText()).contains("public");
        }


        /**
         * 测试不使用模板的情况下,不使用redirect和forward就可以返回页面
         *
         * @throws Exception exception
         * @see ViewController#publicModelAndViewWithoutTemplate()
         */
        @Test
        public void testPublicModelAndViewWithoutTemplate() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/view/public-model-and-view-without-template"))
                    // 判断forward的url
                    .andExpect(MockMvcResultMatchers.forwardedUrl("/public.html"))
                    .andReturn();
        }
    }

    @SpringBootTest(classes = {MVCApplication.class, ViewController.class}, properties = "spring.thymeleaf.enabled=false", webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
    public static class DisableThymeleafApplication {
        /**
         * 测试View
         * 使用postman测试
         *
         * @throws Exception the exception
         */
        @Test
        public void startApplication() throws Exception {
            Thread.sleep(1000000);
        }
    }

    @SpringBootTest(classes = {MVCApplication.class, ViewController.class}, properties = "spring.thymeleaf.enabled=true", webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
    public static class EnableThymeleafApplication {
        /**
         * 测试View
         * 使用postman测试
         *
         * @throws Exception the exception
         */
        @Test
        public void startApplication() throws Exception {
            Thread.sleep(1000000);
        }
    }
}
