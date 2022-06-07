package com.lzy.demo.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//这个Handler可以被多个Channel安全地共享
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {
    // 值保存着超时次数
    private static Map<ChannelHandlerContext, Integer> clientOvertimeMap = new ConcurrentHashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        clientOvertimeMap.put(ctx, 0);
        super.channelActive(ctx);
    }

    /**
     *
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        clientOvertimeMap.put(ctx, 0);
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("ServerHandler receive:" + byteBuf.toString(CharsetUtil.UTF_8));
        // write()是异步的
        ctx.write(byteBuf);
    }

    /**
     * 这一批消息已经读完
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    /**
     * 在读操作异常被抛出时被调用
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        //心跳包检测读超时
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                int overtimeTimes = clientOvertimeMap.getOrDefault(ctx, 0);
                //3次后才关闭连接
                if (overtimeTimes >= 3) {
                    System.out.println("关闭连接");
                    clientOvertimeMap.remove(ctx);
                    ctx.close();
                } else {
                    overtimeTimes++;
                    System.out.println("空闲次数:" + overtimeTimes);
                    clientOvertimeMap.put(ctx, overtimeTimes);
                }
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端已断开");
        clientOvertimeMap.remove(ctx);
        super.channelInactive(ctx);
    }

}
