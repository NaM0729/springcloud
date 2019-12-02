package com.demo.netty.socket.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

@ChannelHandler.Sharable
public class ThreadServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static Logger logger = LoggerFactory.getLogger(ThreadServerHandler.class);

    public static final ThreadServerHandler INSTANCE = new ThreadServerHandler();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msg) throws Exception {
        ByteBuf byteBuf = Unpooled.directBuffer();
        byteBuf.writeBytes(msg);
        Object result = getResult(byteBuf);
        channelHandlerContext.writeAndFlush(result);
    }

    public Object getResult(ByteBuf byteBuf) throws InterruptedException {
        // 90.0% == 1ms
        // 95.0% == 10ms
        // 99.0% == 100ms
        // 99.9% == 1000ms
        int level = ThreadLocalRandom.current().nextInt(1,1000);
        int time ;
        if (level <= 900){
            time = 1;
        }else if (level <=950){
            time = 10;
        }else if (level <=990){
            time = 100;
        }else {
            time = 1000;
        }
        Thread.sleep(Long.valueOf(time));
//        Long result = System.currentTimeMillis() - byteBuf.readLong();
        return byteBuf;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
