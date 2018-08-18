package com.demo.netty.http.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.URI;


/**
 * 处理http协议的传输数据接收
 * @author zyn
 * @date 2018-6-8
 */

public class HttpClient {

    public void connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                    ch.pipeline().addLast(new HttpRequestEncoder());
                    ch.pipeline().addLast(new HttpClienttHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();

            URI uri = new URI("http://127.0.0.1:9090");
            String msg = "{\n" +
                    "\t\"datas\": [{\n" +
                    "\t\t\"avgHeartRate\": \"12\",\n" +
                    "\t\t\"calorie\": \"12\",\n" +
                    "\t\t\"distance\": \"12e\",\n" +
                    "\t\t\"heartRate\": \"233\",\n" +
                    "\t\t\"position\": \"321.1212,1213.1212\",\n" +
                    "\t\t\"sleep\": \"12\",\n" +
                    "\t\t\"stepCounter\": \"12344\",\n" +
                    "\t\t\"timeStamp\": \"2018-10-10 09:11:12\"\n" +
                    "\t},{\n" +
                    "\t\t\"avgHeartRate\": \"12\",\n" +
                    "\t\t\"calorie\": \"12\",\n" +
                    "\t\t\"distance\": \"12e\",\n" +
                    "\t\t\"heartRate\": \"233\",\n" +
                    "\t\t\"position\": \"321.1212,1213.1212\",\n" +
                    "\t\t\"sleep\": \"12\",\n" +
                    "\t\t\"stepCounter\": \"12344\",\n" +
                    "\t\t\"timeStamp\": \"2018-10-10 09:10:12\"\n" +
                    "\t}],\n" +
                    "\t\"deviceId\": \"353537064100591\",\n" +
                    "\t\"deviceType\": \"band\",\n" +
                    "\t\"userId\": \"109\",\n" +
                    "\t\"battery\": \"90\",\n" +
                    "\t\"factoryId\": \"2\"\n" +
                    "\n" +
                    "}";
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,
                    uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));

            // 构建http请求
            request.headers().set(HttpHeaders.Names.HOST, host);
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
            // 发送http请求
            f.channel().write(request);
            f.channel().flush();
            f.channel().closeFuture().sync();
        } finally {
//            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        HttpClient client = new HttpClient();
        client.connect("127.0.0.1", 9090);
    }
}