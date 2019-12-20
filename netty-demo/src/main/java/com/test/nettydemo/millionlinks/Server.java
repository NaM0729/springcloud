package com.test.nettydemo.millionlinks;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author zyn 模拟百万链接
 * @Description
 * @date 2019-12-04 10:20
 */
public final class Server {
    public static void main(String[] args) {
        new Server().start(8000, 100);
    }

    public void start(int beginPort, int nPort) {
        System.out.println("Server starting……");

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_REUSEADDR, true)
                .childHandler(new ConnectionCountHandler());

        for (int i = 0; i < nPort; i++) {
            int port = beginPort + i;
            bootstrap.bind(port).addListener((ChannelFutureListener) future -> System.out.println("bind success in port :" + port));
        }
        System.out.println("server started");
    }
}
