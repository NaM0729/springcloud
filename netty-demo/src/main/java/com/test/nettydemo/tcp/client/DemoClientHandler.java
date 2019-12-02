package com.test.nettydemo.tcp.client;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;

public class DemoClientHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 客户端请求的心跳命令
     */
    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("hb_request",
            CharsetUtil.UTF_8));
    /**
     * 空闲次数
     */
    private AtomicInteger idle_count = new AtomicInteger((1));
    private int idle_count_max = 3;

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) {
//        针对 msg 未object类型的解析 ；同时未做解码设置
//        try {
//            ByteBuf buf = (ByteBuf) msg;
//            byte[] data = new byte[buf.readableBytes()];
//            buf.readBytes(data);
//            System.out.println("server say：" + new String(data).trim());
//        } finally {
//            ReferenceCountUtil.release(msg);
//        }

        System.out.println("Server say:" + msg);
    }


    /**
     * 心跳请求处理
     * 每4秒发送一次心跳请求;
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            //如果写通道处于空闲状态,就发送心跳命令
            if (IdleState.WRITER_IDLE.equals(event.state())) {
                //设置发送次数
                if (idle_count.get() <= idle_count_max) {
                    idle_count.getAndIncrement();
                    //当捕获到 IdleStateEvent，发送心跳到远端，并添加一个监听器，如果发送失败就关闭服务端连接
//                    ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                    ctx.writeAndFlush("hb_request\n");
                    System.out.println("开始发送心跳数据！");
                } else {
                    System.out.println("不再发送数据了！");
                    ctx.channel().close();
                }
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client close ");
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // IOException: 远程主机强迫关闭了一个现有的连接。 异常信息打印
//        cause.printStackTrace();
        ctx.close();
        System.out.println("server close by exception");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.writeAndFlush("客户端已经连接服务端\r\n");
    }
}