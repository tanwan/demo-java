package com.lzy.demo.io.netty;

import org.junit.jupiter.api.Test;

public class NettyTest {

    /**
     * 测试netty服务端, 还可以使用telnet进行测试
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testServer() throws InterruptedException {
        new Starter().startServer();
    }

    /**
     * 测试netty客户端
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testClient() throws InterruptedException {
        new Starter().startClient();
    }
}
