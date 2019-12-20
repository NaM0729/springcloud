package com.test.nettydemo.tcp.thread;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zyn
 * @Description
 * @date 2019-12-10 15:00
 */
@ChannelHandler.Sharable
public class SocketClientHandler extends SimpleChannelInboundHandler<String> {
    public static final ChannelHandler INSTANCE = new SocketClientHandler();
    private static AtomicLong beginTime = new AtomicLong(0);
    private static AtomicLong totalResponseTime = new AtomicLong(0);
    private static AtomicInteger totalRequest = new AtomicInteger(0);
    public static final Thread THREAD = new Thread(() -> {
        try {
            while (true) {
                long duration = System.currentTimeMillis() - beginTime.get();
                if (duration != 0) {
                    System.out.println("qps:" + 1000 * totalRequest.get() / duration + ", " + "avg response time: " + ((float) totalResponseTime.get()) / totalRequest.incrementAndGet());
                    Thread.sleep(2000);
                }
            }
        } catch (InterruptedException ex) {

        }
    });

    protected SocketClientHandler() {
        super();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("qw");
//        ctx.executor().scheduleAtFixedRate(() -> ctx.writeAndFlush(System.currentTimeMillis()), 0, 1, TimeUnit.SECONDS);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("server say:" + s);
        totalResponseTime.addAndGet(System.currentTimeMillis() - Long.parseLong(s));
        totalRequest.incrementAndGet();
        if (beginTime.compareAndSet(0, System.currentTimeMillis())) {
            THREAD.start();
        }
    }
}
