package com.lzy.demo.webflux.handle;

import com.lzy.demo.webflux.entity.WebfluxEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class WebfluxHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 查询单个用户
     *
     * @return 返回Mono :非阻塞单个结果
     */
    public WebfluxEntity getOne() {
        logger.info("getOne");
        // 这边省略了从数据库或者数据
        WebfluxEntity webfluxEntity = new WebfluxEntity();
        webfluxEntity.setId(1);
        webfluxEntity.setMessage("message1");
        return webfluxEntity;
    }

    /**
     * Gets much.
     *
     * @return 返回Flux :非阻塞序列
     * @throws InterruptedException the interrupted exception
     */
    public Stream<WebfluxEntity> getMuch() throws InterruptedException {
        logger.info("getMuch");
        // 这边省略了从数据库或者数据
        WebfluxEntity webfluxEntity1 = new WebfluxEntity();
        webfluxEntity1.setId(1);
        webfluxEntity1.setMessage("message1");
        WebfluxEntity webfluxEntity2 = new WebfluxEntity();
        webfluxEntity2.setId(2);
        webfluxEntity2.setMessage("message2");
        //使用lambda表达式
        return Stream.of(webfluxEntity1, webfluxEntity2);
    }
}
