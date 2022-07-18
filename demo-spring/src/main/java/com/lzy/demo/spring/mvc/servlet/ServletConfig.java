package com.lzy.demo.spring.mvc.servlet;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Servlet配置
 *
 * @author lzy
 * @version v1.0
 * @see org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration
 * @see org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration
 */
@SpringBootConfiguration
@EnableAutoConfiguration(exclude = RedisAutoConfiguration.class)
public class ServletConfig {
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
                System.out.println("requestDestroyed");
            }

            @Override
            public void requestInitialized(ServletRequestEvent sre) {
                System.out.println("requestInitialized");
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
                System.out.println("filter");
                super.doFilter(request, response, chain);
            }
        });
        filterRegistrationBean.addUrlPatterns("/filter");
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
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                resp.getWriter().print("hello world");
            }
        }, "/servlet");
    }

    /**
     * 通过WebServerFactoryCustomizer来自定义配置,ServerProperties其实也是通过WebServerFactoryCustomizer来配置的
     *
     * @return the servlet web server factory customizer
     * @see ServletWebServerFactoryAutoConfiguration#servletWebServerFactoryCustomizer
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> {
            //通过ConfigurableServletWebServerFactory来修改端口
            //factory.setPort(18080);
        };
    }
}
