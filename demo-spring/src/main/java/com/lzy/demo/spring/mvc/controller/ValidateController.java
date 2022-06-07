package com.lzy.demo.spring.mvc.controller;

import com.lzy.demo.spring.mvc.bean.ValidateMessage;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver;

import javax.validation.Valid;

/**
 * 校验
 *
 * @author lzy
 * @version v1.0
 * See <a href = "http://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/"/>
 */
@RestController
@RequestMapping("/validate")
public class ValidateController {

    /**
     * 不为空
     *
     * @param validateMessage the validate message
     * @return the string
     * @see AbstractMessageConverterMethodArgumentResolver#validateIfApplicable(org.springframework.web.bind.WebDataBinder, org.springframework.core.MethodParameter)
     */
    @PostMapping("/not-empty")
    public String notEmpty(@Valid @RequestBody ValidateMessage validateMessage) {
        return validateMessage.toString();
    }


    /**
     * 处理校验的异常
     * @param e exception
     * @return str
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
    protected String handleValidationException(MethodArgumentNotValidException e) {
        System.out.println(e.getBindingResult());
        return "handleValidationException";
    }

}
