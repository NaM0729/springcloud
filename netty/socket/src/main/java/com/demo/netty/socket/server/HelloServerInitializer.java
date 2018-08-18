package com.demo.netty.socket.server;

import com.demo.netty.socket.server.handler.HelloServerToHttpHandler;
import com.demo.netty.socket.util.ByteArrayToHexStrDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 以("\n")为结尾分割的 解码器  --针对控制台测试输入
//        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

        //入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
//        pipeline.addLast(new IdleStateHandler(1000, 0, 0, TimeUnit.SECONDS));

        // 测试心跳
//        pipeline.addLast("handler", new HelloServerHandler());

        // 字节数组转16进制字符串解码  固定长度解码器可以用FixedLengthFrameDecoder
        pipeline.addLast("decoder", new ByteArrayToHexStrDecoder() );
        // 测试转发http请求
        pipeline.addLast("handler", new HelloServerToHttpHandler());

    }
}