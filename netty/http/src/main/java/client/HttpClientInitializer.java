package com.demo.netty.http.client;

import io.netty.channel.ChannelInboundHandlerAdapter;

public class HttpClientInitializer extends ChannelInboundHandlerAdapter {

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        if (msg instanceof HttpResponse)
//        {
//            HttpResponse response = (HttpResponse) msg;
//            System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaders.Names.CONTENT_TYPE));
//        }
//        if(msg instanceof HttpContent)
//        {
//            HttpContent content = (HttpContent)msg;
//            ByteBuf buf = content.content();
//            System.out.println(buf.toString(io.netty.util.CharsetUtil.UTF_8));
//            buf.release();
//        }
//    }
}
