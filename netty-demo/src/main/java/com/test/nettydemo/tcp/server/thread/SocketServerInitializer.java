package com.test.nettydemo.tcp.server.thread;

import com.test.nettydemo.util.ByteArrayToHexStrDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;

public class SocketServerInitializer extends ChannelInitializer<SocketChannel> {

    private EventLoopGroup bussinessGroup;

    public SocketServerInitializer(EventLoopGroup bussinessGroup) {
        this.bussinessGroup = bussinessGroup;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new ByteArrayToHexStrDecoder() );
        pipeline.addLast(bussinessGroup, SocketServerHandler.INSTANCE);
    }
}