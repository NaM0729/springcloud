package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;


public class ThreadClient {

    public static String host = "127.0.0.1";

    public static int port = 8080;

    private static int count = 0;

    public void start(int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        final Bootstrap b = new Bootstrap();
        b.group(group);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_REUSEADDR,true);
        b.handler(new ThreadClientInitializer());
        for (int i = 0; i < 1000; i++) {
            b.connect(host, port).sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadClient threadClient = new ThreadClient();
        threadClient.start(port);
    }

}
