package com.test.nettydemo.tcp.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


public class ReceiverClient{

    public static String host = "127.0.0.1";

    public static int port = 9001;

    public void sendData() {
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

    public static void main(String[] args) {
        new ReceiverClient().sendData();
    }
}
