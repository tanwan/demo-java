/*
 * Created by lzy on 2020/4/21 8:49 PM.
 */
package com.lzy.demo.vertx.server.core.verticle;

import io.vertx.core.AbstractVerticle;

/**
 * @author lzy
 * @version v1.0
 */
public class SampleVerticle extends AbstractVerticle {


    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(req -> {
            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello World!");
        }).listen(8080);
    }
}
