package com.lzy.demo.brave.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brave")
public class BraveController {


    private Logger logger = LoggerFactory.getLogger(getClass());


    @Value("${server.port}")
    private Integer port;

    /**
     * 获取端口
     *
     * @return the integer
     */
    @GetMapping("/port")
    public Integer serverPort() {
        logger.info("port");
        return port;
    }


    @GetMapping("/simple-request")
    public String sleuth2() {
        logger.info("simple-request");
        return "simple-request";
    }
}

