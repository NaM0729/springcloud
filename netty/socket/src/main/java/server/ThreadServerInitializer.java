package com.demo.netty.socket.server;

import com.demo.netty.socket.server.handler.ThreadServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

public class ThreadServerInitializer extends ChannelInitializer<SocketChannel> {

    private EventLoopGroup bussinessGroup ;

    public ThreadServerInitializer(EventLoopGroup bussinessGroup) {
        this.bussinessGroup = bussinessGroup;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("decoder",new FixedLengthFrameDecoder(Long.BYTES));

//        pipeline.addLast("handler", new ThreadServerHandler());

        // 把业务处理逻辑放入线程池中处理。不同业务拥有不同内存空间，不利于内存共享，
//        pipeline.addLast("handler", ThreadPoolServerHandler.INSTANCE);

        // 使用netty原生线程池，不会侵入代码
        pipeline.addLast(bussinessGroup, ThreadServerHandler.INSTANCE);
    }

}
