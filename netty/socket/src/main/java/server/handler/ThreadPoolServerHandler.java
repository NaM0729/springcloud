package com.demo.netty.socket.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.*;

@ChannelHandler.Sharable
public class ThreadPoolServerHandler extends ThreadServerHandler {
    public static final ChannelHandler INSTANCE = new ThreadPoolServerHandler();
    private static ExecutorService threadPool = new ThreadPoolExecutor(60,60,20, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msg) throws Exception {
        ByteBuf byteBuf = Unpooled.directBuffer();
        byteBuf.writeBytes(msg);
        threadPool.submit(() -> {
            Object result = null;
            try {
                result = this.getResult(byteBuf);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            channelHandlerContext.writeAndFlush(result);
        });

    }
}
