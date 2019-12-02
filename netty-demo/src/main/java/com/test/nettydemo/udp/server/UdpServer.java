package com.test.nettydemo.udp.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * @author zyn
 * @Description
 * @date 2019-12-02 11:48
 */
public class UdpServer {

    public void run (int port) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bossGroup)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST,true)
                .handler(new UdpServerHandle());
            bootstrap.bind(port).sync().channel().closeFuture().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        UdpServer udpServer = new UdpServer();
        udpServer.run(8888);
    }
}
