package com.demo.netty.socket.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

public class HelloServerToHttpHandler extends SimpleChannelInboundHandler<String> {

    private Logger logger = LoggerFactory.getLogger(HelloServerToHttpHandler.class);

    private SendHttpByHttpClient sendHttpByHttpClient = new SendHttpByHttpClient();

    /**
     * 服务端处理客户端的核心方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
        public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 收到消息直接打印输出
        logger.info(" {} Say :{} ", ctx.channel().remoteAddress(), msg);
        String hard = msg.substring(0,4);
        if(hard.equals("AA75")){
            logger.info("正在解析数据");
            sendHttpByHttpClient.sendHttpAndJson("发送报文到其他接口");
        }
    }


    public HelloServerToHttpHandler() {
        super();
    }

    /**
     * 客户端与服务端创建链接的时候调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        super.channelActive(ctx);
        logger.info("RamoteAddress :{} active !", ctx.channel().remoteAddress());

        ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");

        sendHttpByHttpClient.connectHttp();

    }


    /**
     * 客户端与服务端断开链接的时候调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        sendHttpByHttpClient.closdHttp();
    }

    /**
     * 服务端接收客户端发送过来的数据结束之后调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 工程出现异常的时候调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}