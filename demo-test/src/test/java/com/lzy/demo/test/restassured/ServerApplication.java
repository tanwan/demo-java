package com.lzy.demo.test.restassured;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.Cookie;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ServerApplication extends AbstractVerticle {

    public static final String TEST_HEADER = "test-header";
    public static final String TEST_COOKIE = "test-cookie";

    private Integer port;

    public ServerApplication(Integer port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new ServerApplication(19001).startServer();
    }

    public void startServer() {
        Vertx v = Vertx.vertx();
        v.deployVerticle(this);
    }

    @Override
    public void start() {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.post("/rest/post/:pathParam").handler(ctx -> {
                    Map<String, Object> result = new HashMap<>();
                    Optional.ofNullable(ctx.pathParam("pathParam"))
                            .ifPresent(p -> result.put("pathParam", p));

                    Optional.ofNullable(ctx.queryParams().get("queryParam"))
                            .ifPresent(p -> result.put("queryParam", p));

                    ctx.response().putHeader(TEST_HEADER, ctx.request().getHeader(TEST_HEADER));
                    Optional.ofNullable(ctx.request().getCookie(TEST_COOKIE)).map(cookie -> Cookie.cookie(TEST_COOKIE, cookie.getValue()))
                            .ifPresent(ctx.response()::addCookie);

                    Optional.ofNullable(ctx.body()).ifPresent(body -> result.put("body", body.asPojo(Map.class)));

                    ctx.json(result);
                }
        );

        router.post("/rest/form-data").handler(ctx ->
                ctx.json(Map.of("method", "formData", "allField", ctx.request().formAttributes().entries()))
        );

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(port)
                .onSuccess(server ->
                        System.out.println("HTTP server started on port " + server.actualPort())
                );
    }

    public void stop() {
        vertx.close().onComplete(server -> System.out.println("HTTP server closed"));
    }
}
