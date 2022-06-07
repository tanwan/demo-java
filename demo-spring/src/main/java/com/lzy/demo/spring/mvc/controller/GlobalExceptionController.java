package com.lzy.demo.spring.mvc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异常的controller
 *
 * @author lzy
 * @version v1.0
 */
@RestController
@RequestMapping("/global")
public class GlobalExceptionController {

    /**
     * 抛出RuntimeException异常
     *
     * @return the string
     *
     */
    @GetMapping("/runtime-exception")
    public String runtimeException() {
        throw new RuntimeException("expect");
    }

    /**
     * 抛出Exception异常
     *
     * @return the string
     * @throws Exception the exception
     */
    @GetMapping("/exception")
    public String exception() throws Exception {
        throw new Exception("expect");
    }
}
