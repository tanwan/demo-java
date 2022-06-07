package com.lzy.demo.io.netty;

import org.junit.jupiter.api.Test;

public class ClientTest {

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
