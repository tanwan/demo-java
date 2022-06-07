package com.lzy.demo.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat",
            CharsetUtil.UTF_8));

    /**
     * 连接成功时调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws InterruptedException {
        ctx.writeAndFlush(Unpooled.copiedBuffer("connect", CharsetUtil.UTF_8));
    }

    /**
     * channelRead0()完成后,SimpleChannelInboundHandler会负责释放用于存放消息的ByteBuf的内存引用
     *
     * @see SimpleChannelInboundHandler#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("client receive: " + msg.toString(CharsetUtil.UTF_8));
    }

    /**
     * 出现异常后调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                // 当写空闲时,发送心跳
                System.out.println("heart beat");
                ctx.channel().writeAndFlush(HEARTBEAT_SEQUENCE.duplicate());
            }
        }
        ctx.fireUserEventTriggered(evt);
    }
}
