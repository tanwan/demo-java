package com.lzy.demo.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;

public class Starter {


    /**
     * 启动客户端
     *
     * @throws InterruptedException Interrupted Exception
     */
    public void startClient() throws InterruptedException {
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


    /**
     * 启动服务端
     *
     * @throws InterruptedException Interrupted Exception
     */
    public void startServer() throws InterruptedException {
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
