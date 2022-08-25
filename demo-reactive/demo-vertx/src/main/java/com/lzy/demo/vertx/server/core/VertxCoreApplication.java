package com.lzy.demo.vertx.server.core;

import com.lzy.demo.vertx.server.core.verticle.EventBusVerticle;
import com.lzy.demo.vertx.server.core.verticle.EventBusVerticle2;
import com.lzy.demo.vertx.server.core.verticle.SimpleVerticle;
import com.lzy.demo.vertx.server.core.verticle.TcpServerVerticle;
import io.vertx.core.Vertx;

public class VertxCoreApplication {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(SimpleVerticle.class.getName());
        vertx.deployVerticle(EventBusVerticle.class.getName());
        //也可以直接用实例
        vertx.deployVerticle(new EventBusVerticle2());
        vertx.deployVerticle(new TcpServerVerticle());
    }
}
