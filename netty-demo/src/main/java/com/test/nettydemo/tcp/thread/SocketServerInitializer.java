package com.test.nettydemo.tcp.thread;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class SocketServerInitializer extends ChannelInitializer<SocketChannel> {

    private EventLoopGroup bussinessGroup;

    public SocketServerInitializer(EventLoopGroup bussinessGroup) {
        this.bussinessGroup = bussinessGroup;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new StringDecoder())
                .addLast("encoder", new StringDecoder())
                .addLast(SocketServerHandler.INSTANCE);

    }
}