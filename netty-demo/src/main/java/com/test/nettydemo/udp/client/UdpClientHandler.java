package com.test.nettydemo.udp.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

/**
 * @author zyn
 * @Description
 * @date 2019-12-02 11:56
 */
public class UdpClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable
            cause)throws Exception{
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        String response=msg.content().toString(CharsetUtil.UTF_8);
        if(response.startsWith("谚语查询结果：")){
            System.out.println(response);
            ctx.close();
        }
    }
}
