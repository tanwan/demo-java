package com.lzy.demo.spring.mvc.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class MvcFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("doFilter");
        chain.doFilter(request, response);
    }

    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
        logger.info("init params :{}", filterConfig.getInitParameter("param"));
    }

    @Override
    public void destroy() {
    }
}
