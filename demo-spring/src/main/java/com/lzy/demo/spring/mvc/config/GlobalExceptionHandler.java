package com.lzy.demo.spring.mvc.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常捕获
 *
 * @author LZY
 * @version v1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理RuntimeException,使用ResponseStatus指定返回代码
     *
     * @param e the e
     * @return the string
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return "global handle runtime exception";
    }

    /**
     * 处理Exception
     *
     * @param e the e
     * @return the string
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception e) {
        e.printStackTrace();
        return "global handle exception";
    }
}
