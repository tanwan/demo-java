/*
 * Created by lzy on 2020/4/26 8:31 PM.
 */
package com.lzy.demo.vertx.server.web;

import com.lzy.demo.vertx.server.web.verticle.WebVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.util.concurrent.TimeUnit;

/**
 * @author lzy
 * @version v1.0
 */
public class VertxWebApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        VertxOptions options = new VertxOptions();
        options.setMaxEventLoopExecuteTime(3000L);
        options.setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS);
        Vertx.vertx(options).deployVerticle(WebVerticle.class.getName());
    }
}
