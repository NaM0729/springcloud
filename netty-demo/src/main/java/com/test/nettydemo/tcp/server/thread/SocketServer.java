package com.test.nettydemo.tcp.server.thread;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SocketServer {

    /**
     * 服务端监听的端口地址  IP：47.95.112.189，端口：9001
     */
    private static final int portNumber = 9001;

    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup bussinessGroup = new NioEventLoopGroup(32);
        try {
            //辅助工具类，用于服务器通道的一系列配置
            ServerBootstrap b = new ServerBootstrap();
            //绑定两个线程组
            b.group(bossGroup, workerGroup);
            //指定NIO的模式
            b.channel(NioServerSocketChannel.class);
            //配置具体的数据处理方式
            b.childHandler(new SocketServerInitializer(bussinessGroup));
            b.option(ChannelOption.SO_RCVBUF, 64 * 1024);
            // 服务器绑定端口监听
            ChannelFuture f = b.bind(portNumber).sync();
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SocketServer socketServer = new SocketServer();
        socketServer.start();
    }
}