package com.lzy.demo.vertx.server.web;

import com.lzy.demo.vertx.server.web.verticle.WebVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.util.concurrent.TimeUnit;

public class VertxWebApplication {

    public static void main(String[] args) {
        VertxOptions options = new VertxOptions();
        options.setMaxEventLoopExecuteTime(3000L);
        options.setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS);
        Vertx.vertx(options).deployVerticle(WebVerticle.class.getName());
        System.out.println("start");
    }
}
