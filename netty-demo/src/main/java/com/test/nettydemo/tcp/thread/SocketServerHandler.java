package com.test.nettydemo.tcp.thread;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@ChannelHandler.Sharable
public class SocketServerHandler extends SimpleChannelInboundHandler<String> {

    public static final ChannelHandler INSTANCE = new SocketServerHandler();

    private static final Logger logger = LoggerFactory.getLogger(SocketServerHandler.class);
    private static AtomicInteger count = new AtomicInteger();
    private static AtomicInteger num = new AtomicInteger(0);
    private static ConcurrentHashMap<ChannelHandlerContext, String> maps = new ConcurrentHashMap<ChannelHandlerContext, String>();

    public SocketServerHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws IOException {
        System.out.println(msg);
        Object result = getResult(msg);
        ctx.channel().writeAndFlush(result);
//        SocketMessageModel socketMessageModel = (SocketMessageModel)msg;
//        num.incrementAndGet();
//        logger.info("{} send num:{}",ctx.channel().remoteAddress(), num);
//
//        String value = maps.get(ctx);
//        if (value == null){
//            maps.put(ctx,socketMessageModel.getId());
//        }
//
//        saveToRedit(socketMessageModel);
    }

    private Object getResult(String msg) {
        int anInt = ThreadLocalRandom.current().nextInt(1, 1000);
        int time;
        if (anInt <= 900) {
            time = 1;
        } else if (anInt <= 950) {
            time = 10;
        } else if (anInt <= 990) {
            time = 100;
        } else {
            time = 1000;
        }
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
        return msg;
    }

    /**
     * 客户端与服务端创建链接的时候调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        count.incrementAndGet();
//        logger.info("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");
//        logger.info("当前连接数:{}", count);

    }

    /**
     * 客户端与服务端断开链接的时候调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        logger.info("RamoteAddress : {} inactive !", ctx.channel().remoteAddress());
//        count.decrementAndGet();
//        logger.info("当前连接数:{}", count);
//        String value = maps.get(ctx);
//        if (value != null) {
//            maps.remove(ctx);
//        }
    }

    /**
     * 工程出现异常的时候调用
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
//        ctx.close();
//        count.decrementAndGet();
//        logger.info("当前连接数:{}", count);
    }
}