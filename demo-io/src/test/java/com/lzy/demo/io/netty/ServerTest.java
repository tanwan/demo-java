package com.lzy.demo.io.netty;

import org.junit.jupiter.api.Test;

public class ServerTest {

    /**
     * 测试netty服务端
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testServer() throws InterruptedException {
        new Starter().startServer();
    }
}
