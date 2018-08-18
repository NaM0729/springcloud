package com.demo.netty.socket.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;


public class HelloClient implements Runnable{

    public static String host = "127.0.0.1";

    public static int port = 9001;

    private static int count =0;

    /**
     * @param args
     * @throws InterruptedException
     * @throws IOException
     */


    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HelloClientInitializer());

            // connect server
            Channel ch = b.connect(host, port).sync().channel();

//             控制台输入
//            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//            for (; ; ) {
//                String line = in.readLine();
//                if (line == null) {
//                    continue;
//
//                }
//                /*
//                 * 向服务端发送在控制台输入的文本 并用"\r\n"结尾
//                 * 之所以用\r\n结尾 是因为我们在handler中添加了 DelimiterBasedFrameDecoder 帧解码。
//                 * 这个解码器是一个根据\n符号位分隔符的解码器。所以每条消息的最后必须加上\n否则无法识别和解码
//                 * */
//                ch.writeAndFlush(line + "\r\n");
//
//            }

            //手动设置数据
            String str="AA7500000000AABBCCDD01235B4C183351554AAA15B4BA0B5b0000013C00000912C06C";
            ch.writeAndFlush(str);

        } finally {
            // The connection is closed automatically on shutdown.
//            group.shutdownGracefully();

        }
    }

    @Override
    public void run() {
        try {
            this.sendData(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void sendData(String threadName) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HelloClientInitializer());

            // 连接服务端
            Channel ch = b.connect(host, port).sync().channel();
        } catch (Exception e){

        } finally {
//            group.shutdownGracefully();

        }
    }

}
