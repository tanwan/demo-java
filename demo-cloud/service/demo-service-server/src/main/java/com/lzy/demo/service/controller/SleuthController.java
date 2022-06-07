package com.lzy.demo.service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SleuthController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 使用sleuth
     *
     * @return the string
     */
    @GetMapping("/sleuth")
    public String sleuth() {
        logger.info("sleuth");
        return "sleuth";
    }

    /**
     * 使用sleuth
     *
     * @return the string
     */
    @GetMapping("/sleuth2")
    public String sleuth2() {
        logger.info("sleuth2");
        return "sleuth2";
    }
}
