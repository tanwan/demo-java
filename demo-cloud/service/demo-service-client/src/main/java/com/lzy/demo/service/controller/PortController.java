package com.lzy.demo.service.controller;

import com.lzy.demo.service.constant.Constants;
import com.lzy.demo.service.service.SimpleFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.Resource;

@RestController
public class PortController {
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private SimpleFeignService simpleFeignService;

    /**
     * 使用ribbon获取端口
     *
     * @return the integer
     */
    @GetMapping("/ribbon/port")
    public Integer ribbonGetPort() {
        return restTemplate.getForObject(Constants.DEMO_SERVICE_SERVER + "/port", Integer.class);
    }


    /**
     * 使用ribbon获取端口
     *
     * @return the integer
     */
    @GetMapping("/feign/port")
    public Integer feignGetPort() {
        return simpleFeignService.serverPort();
    }

    /**
     * ribbon降级方法
     *
     * @return default port
     */
    private Integer defaultRibbonGetPort() {
        return 0;
    }
}
