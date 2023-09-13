package com.lzy.demo.brave.controller;

import com.lzy.demo.brave.service.BraveService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brave")
public class BraveController {

    // 写日志观察日志的traceId和spanId
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private BraveService braveService;

    /**
     * 使用ribbon请求
     *
     * @return the string
     */
    @GetMapping("/ribbon")
    public String ribbon() {
        logger.info("before controller ribbon");
        braveService.ribbon();
        logger.info("after controller ribbon");
        return "ribbon";
    }


    /**
     * 使用feign请求
     *
     * @return the string
     */
    @GetMapping("/feign")
    public String feign() {
        logger.info("before controller feign");
        braveService.feign();
        logger.info("after controller feign");
        return "feign";
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
        braveService.async();
        logger.info("after controller async");
        return "async";
    }

    /**
     * 使用线程
     *
     * @return the string
     * @throws InterruptedException the interrupted exception
     */
    @GetMapping("/thread")
    public String thread() {
        logger.info("before controller thread");
        braveService.thread();
        logger.info("after controller thread");
        return "thread";
    }

    /**
     * 使用restTemplate
     *
     * @return the string
     */
    @GetMapping("/rest-template")
    public String restTemplate() {
        logger.info("before controller restTemplate");
        braveService.restTemplate();
        logger.info("after controller restTemplate");
        return "restTemplate";
    }

    /**
     * 使用customizing
     *
     * @return the string
     */
    @GetMapping("/customizing")
    public String customizing() {
        logger.info("before controller customizing");
        braveService.customizing();
        logger.info("after controller customizing");
        return "customizing";
    }

    @GetMapping("/use-annotation")
    public String useAnnotation() {
        logger.info("before controller useAnnotation");
        braveService.useAnnotation();
        logger.info("after controller useAnnotation");
        return "useAnnotation";
    }

    @GetMapping("/start-scoped-span")
    public String startScopedSpan() {
        logger.info("before controller startScopedSpan");
        braveService.startScopedSpan();
        logger.info("after controller startScopedSpan");
        return "startScopedSpan";
    }

    @GetMapping("/with-span-in-scope")
    public String withSpanInScope() throws InterruptedException {
        logger.info("before controller withSpanInScope");
        braveService.withSpanInScope();
        logger.info("after controller withSpanInScope");
        return "withSpanInScope";
    }
}
