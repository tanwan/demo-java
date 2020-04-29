/*
 * Created by lzy on 2020/4/28 11:01 PM.
 */
package com.lzy.demo.vertx.server.web.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.CookieSameSite;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * The type Web verticle.
 *
 * @author lzy
 * @version v1.0
 */
public class WebVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        //为阻塞EventLoop设置setExpectMultipart
        router.post("/blocking-handler").handler(ctx -> {
            //如果需要使用multipart,一定需要在非阻塞的handler设置setExpectMultipart,并且设置这个需要在BodyHandler之前
            //如果不需要使用multipart,则不需要这段
            ctx.request().setExpectMultipart(true);
            ctx.next();
        });

        //不论是form表单还是json,都需要BodyHandler,才能获取到请求消息
        router.route().handler(BodyHandler.create());
        //精确匹配路径,vert.x会忽略路径后的/,所以/exact-path和/exact-path/是一样的
        router.route().path("/exact-path").handler(this::exactPath);

        //通配符,可以匹配多层路径
        //虽然只有一个星号,但是可以匹配多层路径,比如/asterisk/1,/asterisk/1/2,但是无法匹配/asterisk
        router.route(HttpMethod.GET, "/asterisk/*").handler(this::asterisk);

        //使用:+变量名,然后使用routingContext.request().getParam()获取
        router.get("/path-parameters/:parameters").handler(this::pathParameters);

        //正则表达式
        //或者router.routeWithRegex("/regular/.*")
        router.route().pathRegex("/regular/.*").handler(this::regular);

        //正则表达式(捕获组)
        //使用param0表示第一个捕获组,param1表示第2个捕获组
        router.routeWithRegex("/regular-group/(.*)").handler(this::regularGroup);

        //Form 表单请求
        router.post("/form").handler(this::form);

        //json
        router.post("/json").handler(this::json);

        //阻塞EventLoop
        //阻塞,会阻塞/blocking-handle的EventLoop,不影响其它handler
        //跟非阻塞的区别是,非阻塞默认最多2秒会进行EventLoop,如果此handler执行超过2秒,则会报错
        //而阻塞的话,则会等此handle执行完后才会进行EventLoop
        router.post("/blocking-handler").blockingHandler(this::blockingHandler);

        //cookie
        router.route("/cookie").handler(this::cookie);


        //创建session存储,也可以使用分布式
        SessionStore store = LocalSessionStore.create(vertx);
        //处理handler的
        SessionHandler sessionHandler = SessionHandler.create(store);

        //cookie的sameSite属性,用来控制csrf的
        sessionHandler.setCookieSameSite(CookieSameSite.STRICT);

        //处理session
        router.route().handler(sessionHandler);
        //session
        router.route("/session").handler(this::session);

        //file
        router.post("/file-upload").handler(this::fileUpload);

        //文件下载
        router.route("/file-download").handler(this::fileDownload);

        HttpServer server = vertx.createHttpServer();
        server.requestHandler(router).listen(8080);
    }

    /**
     * 精确匹配路径,vert.x会忽略路径后的/,所以/exact-path和/exact-path/是一样的
     */
    private void exactPath(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        response.end("exact-path");
    }


    /**
     * 通配符,可以匹配多层路径
     */
    private void asterisk(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        response.end("asterisk");
    }


    /**
     * 路径参数
     */
    private void pathParameters(RoutingContext routingContext) {
        String parameters = routingContext.request().getParam("parameters");
        routingContext.response().end(parameters);
    }


    /**
     * 正则表达式
     */
    private void regular(RoutingContext routingContext) {
        String param0 = routingContext.request().getParam("param0");
        routingContext.response().end("regular:" + param0);
    }


    /**
     * 正则表达式(捕获组)
     */
    private void regularGroup(RoutingContext routingContext) {
        //使用param0表示第一个捕获组,param1表示第2个捕获组
        String param0 = routingContext.request().getParam("param0");
        routingContext.response().end("regular:" + param0);
    }


    /**
     * Form 表单请求
     */
    private void form(RoutingContext routingContext) {
        //routingContext.request().formAttributes()获取表单信息
        routingContext.response().end(routingContext.request().formAttributes().toString());
    }

    /**
     * json
     */
    private void json(RoutingContext routingContext) {
        Map map = Json.decodeValue(routingContext.getBodyAsString(), Map.class);
        routingContext.response().end(map.toString());
    }

    /**
     * 阻塞,会阻塞/blocking-handle的EventLoop,不影响其它handler
     * 跟非阻塞的区别是,非阻塞默认最多2秒会进行EventLoop,如果此handler执行超过2秒,则会报错
     * 而阻塞的话,则会等此handle执行完后才会进行EventLoop
     */
    private void blockingHandler(RoutingContext routingContext) {
        System.out.println("blockingHandler");
        try {
            //这边有个神坑的地方,如果马上获取ctx.request().formAttributes(),有概率会为空
            Thread.sleep(50L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String param = routingContext.request().formAttributes().get("param");
        routingContext.response().end(param);
    }

    /**
     * cookie
     */
    private void cookie(RoutingContext routingContext) {
        Cookie cookie = routingContext.getCookie("request-cookie");
        routingContext.addCookie(Cookie.cookie("response-cookie", cookie.getValue()));
        routingContext.response().end(cookie.getValue());
    }

    /**
     * session
     */
    private void session(RoutingContext routingContext) {
        Session session = routingContext.session();
        String value = session.get("sessionKey");
        session.put("sessionKey", "sessionValue");
        routingContext.response().end(Optional.ofNullable(value).orElse(""));
    }

    /**
     * 文件上传
     */
    private void fileUpload(RoutingContext routingContext) {
        Set<FileUpload> uploads = routingContext.fileUploads();
        routingContext.response().setChunked(true);
        System.out.println(routingContext.request().formAttributes().toString());
        for (FileUpload upload : uploads) {
            //从vertx.fileSystem获取文件内容
            Buffer buffer = vertx.fileSystem().readFileBlocking(upload.uploadedFileName());
            routingContext.response().write(buffer);
        }
        routingContext.response().end();
    }

    /**
     * 文件下载
     */
    private void fileDownload(RoutingContext routingContext){
        routingContext.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, "text/plain")
                .putHeader("Content-Disposition", "attachment; filename=\"file-download.txt\"")
                .putHeader(HttpHeaders.TRANSFER_ENCODING, "chunked")
                .setChunked(true)
                //如果是ReadStream的话,可以直接使用Pump.pump()
                //目前vertx没有提供InputStream转ReadStream的方法,不过有其它开发者自行实现了AsyncInputStream
                //文件内容
                .write("hello world")
                //如果文件在本地,直接使用sendFile
                //.sendFile("/Users/lzy/desktop/file.txt")
                .end();
    }
}
