package com.demo.netty.socket.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@ChannelHandler.Sharable
public class ThreadClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    public static final ThreadClientHandler INSTANCE = new ThreadClientHandler();
    private static Logger logger = LoggerFactory.getLogger(ThreadClientHandler.class);
    private static AtomicLong count = new AtomicLong(0);
    private static AtomicLong timeSum = new AtomicLong(0);
    private static AtomicLong begintime = new AtomicLong(0);

    public static final Thread THREAD = new Thread(() -> {
        while(true){
            long timemillis = System.currentTimeMillis() - begintime.get();
            if (timemillis != 0){
                logger.info("qps {},avg response time:{}",1000*count.get()/timemillis,(float)timeSum.get()/count.get());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msg) throws Exception {
        timeSum.addAndGet(System.currentTimeMillis()-msg.readLong());
        count.incrementAndGet();
        if(begintime.compareAndSet(0,System.currentTimeMillis())){
            THREAD.start();
        }


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.executor().scheduleAtFixedRate(() ->{
            ByteBuf byteBuf = ctx.alloc().ioBuffer();
            byteBuf.writeLong(System.currentTimeMillis());
            ctx.channel().writeAndFlush(byteBuf);
        },0,1, TimeUnit.SECONDS);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
