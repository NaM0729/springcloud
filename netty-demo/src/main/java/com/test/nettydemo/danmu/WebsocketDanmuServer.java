package com.test.nettydemo.danmu;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Websocket 聊天服务器-服务端
 * 测试方案：使用浏览监控平台：
 * 进行websocket链接：websocket1 = new WebSocket("ws://localhost:8080/ws");
 * 数据发送： websocket.send("nihao");
 *
 */
public class WebsocketDanmuServer {

    private int port;

    public WebsocketDanmuServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        EventLoopGroup workerGroup = new NioEventLoopGroup(3);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new WebsocketDanmuServerInitializer())
             .option(ChannelOption.SO_BACKLOG, 128)
             .childOption(ChannelOption.SO_KEEPALIVE, true);
    		System.out.println("SnakeGameServer 启动了" + port);
    		
            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port).sync();

            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            
    		System.out.println("SnakeGameServer 关闭了");
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new WebsocketDanmuServer(port).run();

    }
}