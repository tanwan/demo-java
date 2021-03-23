/*
 * Created by lzy on 2020/5/19 11:49 AM.
 */
package com.lzy.demo.vertx.server.core.verticle;

import com.lzy.demo.vertx.server.constatnts.Constants;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lzy
 * @version v1.0
 */
public class EventBusVerticle2 extends AbstractVerticle {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void start() throws Exception {
        vertx.eventBus().consumer(Constants.SIMPLE_EVENT_BUS_ADDRESS, this::consumeSimpleEvent);
        vertx.eventBus().consumer(Constants.PUBLISH_EVENT_BUS_ADDRESS, this::consumePublishEvent);
        vertx.eventBus().consumer(Constants.PUBLISH_EVENT_BUS_ADDRESS, this::consumePublishEvent);
    }

    /**
     * 接收的消息
     */
    private void consumeSimpleEvent(Message<JsonObject> message) {
        //获取消息
        logger.info("EventBusVerticle2:{}", message.body());
        message.reply("EventBusVerticle2 consumeSimpleEvent reply");
    }

    /**
     * 接收的消息
     */
    private void consumePublishEvent(Message<JsonArray> message) {
        //获取消息
        logger.info("EventBusVerticle2:{}", message.body());
        message.reply("EventBusVerticle2 consumeSimpleEvent reply");
    }
}