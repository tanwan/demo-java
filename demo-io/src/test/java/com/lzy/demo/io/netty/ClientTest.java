/*
 * Created by lzy on 16-12-28 上午10:36.
 */
package com.lzy.demo.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

/**
 * The type ClientTest.
 *
 * @author lzy
 * @version v1.0
 */
public class ClientTest {

    /**
     * 测试netty客户端
     */
    @Test
    public void testClient() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            // 只需要工作线程
            bootstrap.group(eventLoopGroup)
                    //确定channel类型
                    .channel(NioSocketChannel.class)
                    //服务器地址
                    .remoteAddress(new InetSocketAddress("127.0.0.1", 9999))
                    // 往ChannelPipeline添加实例
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 客户端这边写空闲时间为5s
                            ch.pipeline().addLast(new IdleStateHandler(0, 5, 0));
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });
            // 阻塞直到连接成功
            ChannelFuture channelFuture = bootstrap.connect().sync();
            // 阻塞直到获取到CloseFuture
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}
