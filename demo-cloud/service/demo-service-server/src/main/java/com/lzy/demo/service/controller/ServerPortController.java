package com.lzy.demo.service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取端口
 *
 * @author lzy
 * @version v1.0
 */
@RestController
public class ServerPortController {

    @Value("${server.port}")
    private Integer port;

    /**
     * 获取端口
     *
     * @return the integer
     */
    @GetMapping("/port")
    public Integer serverPort() {
        return port;
    }
}
