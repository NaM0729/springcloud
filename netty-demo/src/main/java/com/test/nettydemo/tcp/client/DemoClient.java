package com.test.nettydemo.tcp.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class DemoClient {

    public static String host = "127.0.0.1";
    public static int port = 8886;

    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new DemoClientInitializer());
            Channel channel = b.connect(host, port).sync().channel();
//             控制台输入
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            for (; ; ) {
                String line = in.readLine();
                if (line == null) {
                    continue;

                }
                channel.writeAndFlush(Unpooled.copiedBuffer((line + "\r\n").getBytes()));
            }


        } finally {
//            group.shutdownGracefully();
        }
    }
}
