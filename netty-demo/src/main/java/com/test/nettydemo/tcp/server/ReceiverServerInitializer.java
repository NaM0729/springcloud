package com.test.nettydemo.tcp.server;

import com.test.nettydemo.util.ByteArrayToHexStrDecoder;
import com.test.nettydemo.util.HexStrToByteArrayEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author zyn
 * @Description
 * @date 2019-12-02 14:36
 */
public class ReceiverServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("decoder", new HexStrToByteArrayEncoder());
        pipeline.addLast("encoder", new ByteArrayToHexStrDecoder());
        // 服务端的逻辑
        pipeline.addLast("handler", new ReceiverServerHandler());


    }
}
