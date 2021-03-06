/*
 * Created by lzy on 2020/4/21 8:44 PM.
 */
package com.lzy.demo.vertx.server.core;

import com.lzy.demo.vertx.server.core.verticle.EventBusVerticle;
import com.lzy.demo.vertx.server.core.verticle.EventBusVerticle2;
import com.lzy.demo.vertx.server.core.verticle.SimpleVerticle;
import com.lzy.demo.vertx.server.core.verticle.TcpServerVerticle;
import io.vertx.core.Vertx;

/**
 * @author lzy
 * @version v1.0
 */
public class VertxCoreApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(SimpleVerticle.class.getName());
        vertx.deployVerticle(EventBusVerticle.class.getName());
        //也可以直接用实例
        vertx.deployVerticle(new EventBusVerticle2());
        vertx.deployVerticle(new TcpServerVerticle());
    }
}
