package com.demo.netty.socket.client;


import com.demo.netty.socket.util.ByteArrayToHexStrDecoder;
import com.demo.netty.socket.util.HexStrToByteArrayEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ReceiverClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

//        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

        // 16进制字符串转换成字节数组转码器
        ChannelPipeline entries = pipeline.addLast("encoder", new HexStrToByteArrayEncoder()  );

        pipeline.addLast("decoder", new ByteArrayToHexStrDecoder());
        // 客户端的逻辑
        pipeline.addLast("handler", new ReceiverClientHandler());


    }
}