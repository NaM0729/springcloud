package com.test.nettydemo.tcp.client;


import com.test.nettydemo.util.ByteArrayToHexStrDecoder;
import com.test.nettydemo.util.HexStrToByteArrayEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ReceiverClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 16进制字符串转换成字节数组转码器
        pipeline.addLast("encoder", new HexStrToByteArrayEncoder());

        pipeline.addLast("decoder", new ByteArrayToHexStrDecoder());
        // 客户端的逻辑
        pipeline.addLast("handler", new ReceiverClientHandler());


    }
}