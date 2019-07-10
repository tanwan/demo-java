/*
 * Created by lzy on 16-12-28 上午10:07.
 */
package com.lzy.demo.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

/**
 * The type ServerTest.
 *
 * @author lzy
 * @version v1.0
 */
public class ServerTest {

    /**
     * 测试netty服务端
     */
    @Test
    public void testServer() throws InterruptedException {
        // boss线程,用来负责接收新的连接
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
        // work线程
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossLoopGroup, eventLoopGroup)
                    // 设置指定的Channel
                    .channel(NioServerSocketChannel.class)
                    // 设置监听端口
                    .localAddress(new InetSocketAddress(9999))
                    // 这个handler是处理ServerChannel的
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    // childHandler是处理接受连接的客户端Channel的
                    // 这边添加的是ChannelInitializer,当调用ChannelInitializer.initChannel(ChannelHandlerContext),会添加自定义的ChannelHandler
                    // 然后把自己从Pipeline删除掉
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new IdleStateHandler(10, 0, 0));
                            ch.pipeline().addLast(new ServerHandler());
                        }
                    });
            // 阻塞直到完成绑定服务器
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            // 阻塞直到获取到CloseFuture
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 关闭EventLoopGroup,并释放资源
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}
