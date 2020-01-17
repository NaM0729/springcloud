package com.test.nettydemo.danmu;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 服务端 ChannelInitializer
 *
 */
public class WebsocketDanmuServerInitializer extends
        ChannelInitializer<SocketChannel> {	//1

	@Override
    public void initChannel(SocketChannel ch) throws Exception {//2
		 ChannelPipeline pipeline = ch.pipeline();
		 // 请求解码
		pipeline.addLast("http-decodec",new HttpRequestDecoder());
		// 请求头和请求体大小限定
		pipeline.addLast("http-aggregator",new HttpObjectAggregator(65536));
		// 响应编码
		pipeline.addLast("http-encodec",new HttpResponseEncoder());
		pipeline.addLast("http-chunked",new ChunkedWriteHandler());
       /*
			pipeline.addLast(new HttpServerCodec());
			pipeline.addLast(new HttpObjectAggregator(64*1024));
			pipeline.addLast(new ChunkedWriteHandler());
		*/
		pipeline.addLast("http-request",new HttpRequestHandler("/ws"));
		// 实现websocket 协议（的编码与解码）
		pipeline.addLast("WebSocket-protocol",new WebSocketServerProtocolHandler("/ws"));
		// 实现弹幕发送业务
		pipeline.addLast("WebSocket-request",new TextWebSocketFrameHandler());

    }
}
