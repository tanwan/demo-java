package com.lzy.demo.io.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.Cookie;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ServerApplication extends AbstractVerticle {

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


        router.get("/rest/get/:pathParam").handler(ctx -> {
                    String pathParam = ctx.pathParam("pathParam");
                    System.out.println(ctx.queryParams());
                    String queryParam = ctx.queryParams().get("queryParam");
                    ctx.json(Map.of("method", "get", "pathParam", pathParam, "queryParam", queryParam));
                }
        );

        router.post("/rest/post").handler(ctx ->
                ctx.json(Map.of("method", "post", "body", ctx.body().asPojo(Map.class)))
        );

        router.put("/rest/put/:id").handler(ctx -> {
                    Long id = Long.parseLong(ctx.pathParam("id"));
                    Map body = ctx.body().asPojo(Map.class);
                    ctx.json(Map.of("method", "put", "id", id, "body", body));
                }
        );

        router.delete("/rest/delete/:id").handler(ctx ->
                ctx.json(Map.of("method", "delete", "id", Long.parseLong(ctx.pathParam("id"))))
        );


        router.get("/rest/header").handler(ctx -> {
                    System.out.println(ctx.request().headers());
                    String headerKey = ctx.request().getHeader("headerKey");
                    ctx.response().putHeader("headerKey", "headerKey override");
                    ctx.json(Map.of("method", "header", "headerKey", headerKey));
                }
        );

        router.get("/rest/cookie").handler(ctx -> {
                    ctx.request().cookies().stream().map(c -> c.getName() + ":" + c.getValue()).forEach(System.out::println);
                    String cookieKey = ctx.request().getCookie("cookieKey").getValue();
                    ctx.response().addCookie(Cookie.cookie("addCookie", URLEncoder.encode("add cookie value", StandardCharsets.UTF_8)));
                    ctx.json(Map.of("method", "cookie", "cookieKey", cookieKey));
                }
        );

        router.post("/rest/form-data").handler(ctx -> {
                    ctx.json(Map.of("method", "formData", "allField", ctx.request().formAttributes().toString()));
                }
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
