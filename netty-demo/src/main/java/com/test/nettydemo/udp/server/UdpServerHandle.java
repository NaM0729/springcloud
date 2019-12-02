package com.test.nettydemo.udp.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ThreadLocalRandom;

/**
 * @author zyn
 * @Description
 * @date 2019-12-02 11:53
 */
public class UdpServerHandle extends SimpleChannelInboundHandler<DatagramPacket> {
    private static final String[] DICTIONARY = {"小葱拌豆腐，一穷二白", "只要功夫深，铁棒磨成针", "山中无老虎，猴子称霸王"};

    private String nextQuote() {
        //线程安全随机类，避免多线程环境发生错误
        int quote = ThreadLocalRandom.current().nextInt(DICTIONARY.length);
        return DICTIONARY[quote];
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
        cause.printStackTrace();
    }

    @Override
    //接收Netty封装的DatagramPacket对象，然后构造响应消息
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
    //利用ByteBuf的toString()方法获取请求消息
        String req = datagramPacket.content().toString(CharsetUtil.UTF_8);
        System.out.println(req);
//        if ("谚语字典查询?".equals(req)) {
//            //创建新的DatagramPacket对象，传入返回消息和目的地址（IP和端口）
//            channelHandlerContext.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(
//                    "谚语查询结果：" + nextQuote(), CharsetUtil.UTF_8), datagramPacket.sender()));
//        }
    }
}
