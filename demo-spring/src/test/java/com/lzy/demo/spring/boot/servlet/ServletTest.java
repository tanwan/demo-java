package com.lzy.demo.spring.boot.servlet;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Servlet测试
 * EmbeddedServletContainerCustomizer(xxxCustomizer,XXXconfigurer)
 * ,DispatcherServletAutoConfiguration
 *
 * @author lzy
 * @version v1.0
 * @see org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration
 */
@SpringBootTest(classes = {ServletTest.ServletController.class, ServletTest.ServletConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
public class ServletTest {

    /**
     * 测试servlet
     * 有SpringBootTest可以自动注入TestRestTemplate
     *
     * @param testRestTemplate the test rest template
     * @see org.springframework.boot.test.web.client.TestRestTemplateContextCustomizerFactory
     */
    @Test
    public void testServlet(@Autowired TestRestTemplate testRestTemplate) {
        assertEquals("hello world", testRestTemplate.getForObject("/test-servlet", String.class));
    }

    /**
     * 测试过滤器
     *
     * @param mockMvc the mock mvc
     * @throws Exception the exception
     */
    @Test
    public void testFilter(@Autowired MockMvc mockMvc) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test-filter"))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 配置
     */
    @SpringBootConfiguration
    @EnableAutoConfiguration(exclude = RedisAutoConfiguration.class)
    public static class ServletConfig {
        /**
         * 用来注册servlet的监听,可以注册多种事件
         *
         * @return the servlet listener registration bean
         * @see ServletListenerRegistrationBean
         */
        @Bean
        public ServletListenerRegistrationBean<ServletRequestListener> servletListenerRegistrationBean() {
            return new ServletListenerRegistrationBean<>(new ServletRequestListener() {
                @Override
                public void requestDestroyed(ServletRequestEvent sre) {
                    log.info("requestDestroyed");
                }

                @Override
                public void requestInitialized(ServletRequestEvent sre) {
                    log.info("requestInitialized");
                }
            });
        }

        /**
         * 注册过滤器
         *
         * @return the filter registration bean
         */
        @Bean
        public FilterRegistrationBean<HttpFilter> filterRegistrationBean() {
            FilterRegistrationBean<HttpFilter> filterRegistrationBean = new FilterRegistrationBean<>();
            filterRegistrationBean.setFilter(new HttpFilter() {
                @Override
                protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
                    log.info("filter");
                    super.doFilter(request, response, chain);
                }
            });
            filterRegistrationBean.addUrlPatterns("/test-filter");
            return filterRegistrationBean;
        }

        /**
         * 注册servlet
         *
         * @return the servlet registration bean
         */
        @Bean
        public ServletRegistrationBean<HttpServlet> servletRegistrationBean() {
            return new ServletRegistrationBean<>(new HttpServlet() {
                @Override
                protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                    resp.getWriter().print("hello world");
                }
            }, "/test-servlet");
        }

        /**
         * 通过WebServerFactoryCustomizer来自定义配置,ServerProperties其实也是通过WebServerFactoryCustomizer来配置的
         *
         * @param serverProperties the server properties
         * @return the servlet web server factory customizer
         * @see ServletWebServerFactoryAutoConfiguration#servletWebServerFactoryCustomizer
         */
        @Bean
        public WebServerFactoryCustomizer webServerFactoryCustomizer(
                ServerProperties serverProperties) {
            return new WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>() {
                @Override
                public void customize(ConfigurableServletWebServerFactory factory) {
                    //通过ConfigurableServletWebServerFactory来修改端口
                    factory.setPort(8080);
                }
            };
        }
    }


    @RestController
    public static class ServletController {

        @GetMapping("/test-filter")
        public String testFilter() {
            return "success";
        }
    }
}
