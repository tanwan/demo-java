package com.lzy.demo.spring.mvc.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 *
 * @author LZY
 * @version v1.0
 * @see org.springframework.web.servlet.DispatcherServlet#doDispatch(HttpServletRequest, HttpServletResponse)
 */
public class MvcInterceptor implements AsyncHandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 在调用Controller之前调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //在这里不能对request里的InputStream做操作,这里如果对InputStream做操作,后面就获取不了InputStream了
        logger.info("preHandle");
        return true;
    }

    /**
     * 在调用Controller之后调用
     */
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        logger.info("postHandle");
    }

    /**
     * 在doDispatch()调用postHandle()之后调用
     */
    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logger.info("afterCompletion");
    }

    /**
     * 异步开始处理后调用,先调用这里,再开始异步里的处理
     */
    @Override
    public void afterConcurrentHandlingStarted(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("afterConcurrentHandlingStarted");
    }

}
