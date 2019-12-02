package client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceiverClientHandler extends SimpleChannelInboundHandler<String> {

    private static Logger logger= LoggerFactory.getLogger(ReceiverClientHandler.class);
//    public RedisReceiver redisReceiver = SpringUtil.getBean(RedisReceiver.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
//        redisReceiver.receiveMessage("status");
        logger.info("Server say : {}" , msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client active ");
//        redisReceiver.receiveMessage("status");
        String str="AA757cb4e783634db40001235B4C183351554AAA15B4BA0B5b0000013C00000912C06C";

        ctx.writeAndFlush(str);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client close ");
        super.channelInactive(ctx);
    }

}