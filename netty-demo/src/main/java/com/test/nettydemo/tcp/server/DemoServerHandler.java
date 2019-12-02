package com.test.nettydemo.tcp.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class DemoServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 空闲次数
     */
    private AtomicInteger idle_count = new AtomicInteger(1);
    private int idle_count_max = 3;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
//        当 msg 未object时则采用一下方式解析 ；同时未做解码设置
//        ByteBuf buf = (ByteBuf) msg;
//        byte[] data = new byte[buf.readableBytes()];
//        buf.readBytes(data);
//        String request = new String(data, "utf-8");
//        System.out.println("client say:" + LocalDateTime.now() + ": " + request);

        System.out.println("client say:" + msg);
        //如果是心跳命令，则发送给客户端;否则什么都不做
        if ("hb_request".equals(msg)) {
            channelHandlerContext.writeAndFlush("服务端成功收到心跳信息");
            idle_count.set(1);
            channelHandlerContext.flush();
        } else {
            // 返回客户端消息 - 我已经接收到了你的消息
            channelHandlerContext.writeAndFlush("Received your message !" + msg + "\n");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("服务端已经开始监控\r\n".getBytes()));
    }

    /**
     * 客户端与服务端断开链接的时候调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    /**
     * 服务端接收客户端发送过来的数据结束之后调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 心跳超时处理逻辑
     *
     * @param ctx
     * @param obj
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
            //如果读通道处于空闲状态，说明没有接收到心跳命令
            if (IdleState.READER_IDLE.equals(event.state())) {
                System.out.println("已经5秒没有接收到客户端的信息了");
                if (idle_count.get() > idle_count_max) {
                    System.out.println("关闭这个不活跃的channel");
                    ctx.channel().close();
                }
                idle_count.getAndIncrement();
            }
        } else {
            super.userEventTriggered(ctx, obj);
        }

    }
}