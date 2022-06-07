package com.lzy.demo.vertx.server.core.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TCP服务端
 *
 * @author lzy
 * @version v1.0
 */
public class TcpServerVerticle extends AbstractVerticle {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void start() throws Exception {
        NetServerOptions options = new NetServerOptions();
        //设置网络日志,只能用于调试,此日志是直接用netty记录
        options.setLogActivity(true);
        NetServer server = vertx.createNetServer(options);
        server.connectHandler(socket -> {
            socket.handler(buffer -> {
                logger.info("server received:{}", buffer.toString());
                socket.write("server hello world");
            });
        });

        //listen代码需要在connectHandler后面
        server.listen(4321, "0.0.0.0", res -> {
            if (res.succeeded()) {
                logger.info("Server is now listening!");
            } else {
                logger.info("failed");
            }
        });
    }
}
