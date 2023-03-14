package com.lzy.demo.service.controller;

import com.lzy.demo.service.constant.Constants;
import com.lzy.demo.service.service.BraveService;
import com.lzy.demo.service.service.SimpleFeignService;
import com.lzy.demo.service.service.SleuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/sleuth")
public class SleuthController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private SimpleFeignService simpleFeignService;

    @Resource
    private SleuthService sleuthService;

    @Resource
    private BraveService braveService;

    /**
     * 使用ribbon
     *
     * @return the string
     */
    @GetMapping("/ribbon")
    public String ribbon() {
        logger.info("sleuth ribbon");
        restTemplate.getForObject(Constants.DEMO_SERVICE_SERVER + "/sleuth2", String.class);
        return restTemplate.getForObject(Constants.DEMO_SERVICE_SERVER + "/sleuth", String.class);
    }


    /**
     * 使用feign
     *
     * @return the string
     */
    @GetMapping("/feign")
    public String feign() {
        logger.info("sleuth feign");
        simpleFeignService.sleuth2();
        return simpleFeignService.sleuth();
    }


    /**
     * 使用@Async
     *
     * @return the string
     * @throws InterruptedException the interrupted exception
     */
    @GetMapping("/async")
    public String async() throws InterruptedException {
        logger.info("before controller async");
        sleuthService.async();
        logger.info("after controller async");
        return "success";
    }

    /**
     * 使用线程
     *
     * @return the string
     * @throws InterruptedException the interrupted exception
     */
    @GetMapping("/thread")
    public String thread() throws InterruptedException {
        logger.info("before controller thread");
        sleuthService.thread();
        logger.info("after controller thread");
        return "success";
    }

    /**
     * 使用restTemplate
     *
     * @return the string
     */
    @GetMapping("/restTemplate")
    public String restTemplate() {
        logger.info("before controller restTemplate");
        sleuthService.restTemplate();
        logger.info("after controller restTemplate");
        return "success";
    }

    /**
     * 使用customizing
     *
     * @return the string
     */
    @GetMapping("/brave/customizing")
    public String customizing() {
        logger.info("before controller customizing");
        braveService.customizing();
        logger.info("after controller customizing");
        return "success";
    }

    private String defaultRibbon() {
        return "fallback";
    }
}
