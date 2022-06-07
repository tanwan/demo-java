package com.lzy.demo.shiro.controller;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常捕获
 *
 * @author LZY
 * @version v1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理认证异常
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        e.printStackTrace();
        return new ResponseEntity<>("AuthenticationException", HttpStatus.UNAUTHORIZED);
    }

    /**
     * 处理权限不足,因为这个是基于spring mvc的,所以只能拦截到使用注解的权限不足
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<String> handleAuthorizationException(AuthorizationException e) {
        e.printStackTrace();
        return new ResponseEntity<>("AuthorizationException", HttpStatus.FORBIDDEN);
    }
}
