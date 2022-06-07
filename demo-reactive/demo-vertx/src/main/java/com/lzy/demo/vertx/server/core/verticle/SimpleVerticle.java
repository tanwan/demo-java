package com.lzy.demo.vertx.server.core.verticle;

import com.lzy.demo.vertx.server.constatnts.Constants;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleVerticle extends AbstractVerticle {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(req -> {
            if ("/event-bus".equals(req.path())) {
                //可以指定消息的选项
                DeliveryOptions options = new DeliveryOptions();
                options.addHeader("simple-header", "simple-value");
                JsonObject jsonObject = new JsonObject();
                jsonObject.put("key", "hello world");
                //request最多发送给一个handle
                vertx.eventBus().request(Constants.SIMPLE_EVENT_BUS_ADDRESS, jsonObject, options, result -> {
                    //这边是异步执行的
                    if (result.succeeded()) {
                        logger.info(result.result().body().toString());
                    }
                });
                //没有调用end其实这次请求是阻塞的
                req.response().end("event-bus");
            } else if ("/publish".equals(req.path())) {
                JsonArray jsonArray = new JsonArray();
                jsonArray.add(23);
                //publish可以发送给多个
                vertx.eventBus().publish(Constants.PUBLISH_EVENT_BUS_ADDRESS, jsonArray);
                req.response().end("publish");
            } else if ("/timeout".equals(req.path())) {
                DeliveryOptions options = new DeliveryOptions();
                options.setSendTimeout(1000L);
                vertx.eventBus().request(Constants.TIMEOUT_EVENT_BUS_ADDRESS, null, options, result -> {
                    logger.info("result:{}", result.succeeded());
                });
                req.response().end("timeout");
            } else {
                req.response()
                        .putHeader("content-type", "text/plain")
                        .end("Hello World!");
            }
        }).listen(8080);
    }
}
