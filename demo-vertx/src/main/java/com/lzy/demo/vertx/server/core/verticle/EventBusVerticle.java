/*
 * Created by lzy on 2020/5/19 10:40 AM.
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
public class EventBusVerticle extends AbstractVerticle {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void start() throws Exception {
        vertx.eventBus().consumer(Constants.SIMPLE_EVENT_BUS_ADDRESS, this::consumeSimpleEvent);
        vertx.eventBus().consumer(Constants.PUBLISH_EVENT_BUS_ADDRESS, this::consumePublishEvent);
        vertx.eventBus().consumer(Constants.TIMEOUT_EVENT_BUS_ADDRESS, this::consumeTimeoutEvent);
    }

    /**
     * 接收的消息
     */
    private void consumeSimpleEvent(Message<JsonObject> message) {
        //获取消息
        logger.info("EventBusVerticle:{}", message.body());
        //返回消息
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        message.reply("EventBusVerticle consumeSimpleEvent reply");
    }

    /**
     * 接收的消息
     */
    private void consumePublishEvent(Message<JsonArray> message) {
        //获取消息
        logger.info("EventBusVerticle:{}", message.body());
        message.reply("EventBusVerticle consumePublishEvent reply");
    }

    /**
     * 接收的消息
     */
    private void consumeTimeoutEvent(Message<JsonObject> message) {
        //返回消息
        try {
            Thread.sleep(1500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        message.reply("EventBusVerticle consumeTimeoutEvent reply");
    }
}
