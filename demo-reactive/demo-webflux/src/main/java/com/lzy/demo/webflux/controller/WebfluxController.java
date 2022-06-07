package com.lzy.demo.webflux.controller;

import com.lzy.demo.webflux.entity.WebfluxEntity;
import com.lzy.demo.webflux.handle.WebfluxHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@RestController
@RequestMapping("/webflux")
public class WebfluxController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private WebfluxHandler webfluxHandler;

    /**
     * 查询单个用户
     *
     * @return 返回Mono :非阻塞单个结果
     */
    @GetMapping("/one")
    public Mono<WebfluxEntity> getOne() {
        logger.info("getOne");
        return Mono.create(t -> t.success(webfluxHandler.getOne()));
    }

    /**
     * Gets much.
     *
     * @return 返回Flux :非阻塞序列
     * @throws InterruptedException the interrupted exception
     */
    @GetMapping("/much")
    public Flux<WebfluxEntity> getMuch() throws InterruptedException {
        logger.info("getMuch");
        return Flux.fromStream(webfluxHandler.getMuch());
    }

}
