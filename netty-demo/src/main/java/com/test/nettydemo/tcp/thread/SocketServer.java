package com.test.nettydemo.tcp.thread;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SocketServer {

    /**
     * 服务端监听的端口地址  IP：47.95.112.189，端口：9001
     */
    private static final int portNumber = 9001;

    public static void main(String[] args)  {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup bussinessGroup = new NioEventLoopGroup(32);
        //辅助工具类，用于服务器通道的一系列配置
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new SocketServerInitializer(bussinessGroup))
                .option(ChannelOption.SO_REUSEADDR, true);

        b.bind(portNumber).addListener((ChannelFutureListener) future -> System.out.println("bind success in port :" + portNumber));
    }
}