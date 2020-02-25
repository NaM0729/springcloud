package com.test.nettydemo.tcp.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author zyn
 * @Description
 * @date 2019-12-19 16:37
 */
public class ChatClient {
    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        chatClient.start();
    }

    private void start() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(bossGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast("frame", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                                    .addLast("decode", new StringDecoder())
                                    .addLast("encode", new StringEncoder())
                                    .addLast(new ChatClientHandle());
                        }
                    });
            ChannelFuture future = bootstrap.connect("127.0.0.1", 80).addListener((ChannelFutureListener) future1 -> {
                if (!future1.isSuccess()) {
                    System.out.println("超时");
                } else {
                    System.out.println("连接成功");
                }
            }).sync();

//            // 控制台输入
            Scanner scanner = new Scanner(System.in);
//            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                String next = scanner.next();
                future.channel().writeAndFlush(next + "\n");
            }

            // 阻塞通道关闭 用于非控制台输入
//            future.channel().closeFuture().sync();


        } catch (Exception e) {

        } finally {
            bossGroup.shutdownGracefully();
        }
    }
}
