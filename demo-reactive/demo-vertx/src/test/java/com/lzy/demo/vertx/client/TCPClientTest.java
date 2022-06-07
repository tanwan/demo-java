package com.lzy.demo.vertx.client;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import org.junit.jupiter.api.Test;

public class TCPClientTest {


    /**
     * 测试tcp客户端
     * @see com.lzy.demo.vertx.server.core.verticle.TcpServerVerticle
     * @throws InterruptedException interruptedException
     */
    @Test
    public void testClient() throws InterruptedException {
        Vertx vertx = Vertx.vertx();
        NetClientOptions options = new NetClientOptions().setConnectTimeout(10000);
        //设置网络日志,只能用于调试,此日志是直接用netty记录
        options.setLogActivity(true);
        NetClient client = vertx.createNetClient(options);
        client.connect(4321, "localhost", res -> {
            if (res.succeeded()) {
                System.out.println("Connected!");
                NetSocket socket = res.result();
                socket.handler(buffer -> {
                    System.out.println("client receive:" + buffer);
                });
                socket.write("client hello world");
            } else {
                System.out.println("Failed to connect: " + res.cause().getMessage());
            }
        });
        Thread.sleep(30000L);
    }
}
