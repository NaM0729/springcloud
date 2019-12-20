package com.test.nettydemo.millionlinks;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author zyn
 * @Description
 * @date 2019-12-04 10:32
 */
public final class Client {
        private static String ip = "192.168.100.91";
//    private static String ip = "127.0.0.1";

    public static void main(String[] args) {
        new Client().start(8000, 100);
    }

    public void start(final int beginPort, int nPort) {
        System.out.println("client starting ...");
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        final Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                    }

                });
        int index = 0;
        int port;
        while (!Thread.interrupted()) {
            port = beginPort + index;
            try {
                ChannelFuture channelFuture = bootstrap.connect(ip, port);
                channelFuture.addListener((ChannelFutureListener) future -> {
                    if (!future.isSuccess()) {
                        System.out.println("connect failed ,exit!");
                        System.exit(0);
                    }
                });
                channelFuture.get();
            } catch (Exception e) {
            }
            if (++index == nPort) {
                index = 0;
            }
        }
    }
}
