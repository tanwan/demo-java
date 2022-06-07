package com.lzy.demo.vertx.server.core.verticle;

import com.lzy.demo.vertx.server.constatnts.Constants;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBusVerticle2 extends AbstractVerticle {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void start() throws Exception {
        vertx.eventBus().consumer(Constants.SIMPLE_EVENT_BUS_ADDRESS, this::consumeSimpleEvent);
        vertx.eventBus().consumer(Constants.PUBLISH_EVENT_BUS_ADDRESS, this::consumePublishEvent);
        vertx.eventBus().consumer(Constants.PUBLISH_EVENT_BUS_ADDRESS, this::consumePublishEvent);
    }


    private void consumeSimpleEvent(Message<JsonObject> message) {
        //获取消息
        logger.info("EventBusVerticle2:{}", message.body());
        message.reply("EventBusVerticle2 consumeSimpleEvent reply");
    }

    private void consumePublishEvent(Message<JsonArray> message) {
        //获取消息
        logger.info("EventBusVerticle2:{}", message.body());
        message.reply("EventBusVerticle2 consumeSimpleEvent reply");
    }
}
