package com.demo.netty.socket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;


public class ReceiverClient{

    public static String host = "127.0.0.1";

    public static int port = 9001;

    public void sendData() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ReceiverClientInitializer());

            // 连接服务端
            Channel ch = b.connect(host, port).sync().channel();
        } catch (Exception e){

        } finally {
//            group.shutdownGracefully();

        }
    }

}
