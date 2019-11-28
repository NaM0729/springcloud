package com.demo.netty.http.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HelloServer {

    private static final int portNumber = 9090;

    public void start() throws InterruptedException {
        //用于处理服务器端接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //辅助工具类，用于服务器通道的一系列配置
            ServerBootstrap b = new ServerBootstrap();
            //绑定两个线程组
            b.group(bossGroup, workerGroup);
            //指定NIO的模式
            b.channel(NioServerSocketChannel.class);
            //配置具体的数据处理方式
            b.childHandler(new HelloServerInitializer());


            // 可以简写为
            b.bind(portNumber).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        HelloServer helloServer = new HelloServer();
        helloServer.start();
    }
}