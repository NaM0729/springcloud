package com.test.nettydemo.tcp.server;

import com.test.nettydemo.tcp.client.ReceiverClientHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zyn
 * @Description
 * @date 2019-12-02 14:37
 */
public class ReceiverServerHandler extends SimpleChannelInboundHandler<String> {

    private static Logger logger= LoggerFactory.getLogger(ReceiverClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        logger.info("Client say : {}" , msg);
        ctx.writeAndFlush("AA757cb4e783634db40001235B4C183351554AAA15B4BA0B5b0000013C00000912C06C");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Server active ");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Server close ");
        super.channelInactive(ctx);
    }
}
