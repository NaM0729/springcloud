package com.test.nettydemo.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ByteArrayToHexStrDecoder extends ByteToMessageDecoder {
    Logger logger = LoggerFactory.getLogger(ByteArrayToHexStrDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes() == 35 ){
            logger.debug("ByteToIntegerDecoder decode msg is " + byteBuf.readableBytes());
            // Read integer from inbound ByteBuf
            // add to the List of decodec messages
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            String s = ConvertUtil.byteArrayToHexStr(bytes);
            list.add(s);
        }
    }
}
