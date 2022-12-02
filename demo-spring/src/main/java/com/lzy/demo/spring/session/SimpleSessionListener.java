package com.lzy.demo.spring.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

/**
 * session的监听
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class SimpleSessionListener implements HttpSessionListener {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("sessionCreated:{}", se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("sessionDestroyed:{}", se.getSession().getId());
    }
}
