/*
 * Created by lzy on 17-5-24 下午3:05.
 */
package com.lzy.demo.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author lzy
 * @version v1.0
 */
public class AtFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("at");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
