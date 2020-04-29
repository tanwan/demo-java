/*
 * Created by lzy on 2020/4/21 8:44 PM.
 */
package com.lzy.demo.vertx.server.core;

import com.lzy.demo.vertx.server.core.verticle.SampleVerticle;
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
        vertx.deployVerticle(SampleVerticle.class.getName());
    }
}
