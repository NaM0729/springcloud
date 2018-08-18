package com.demo.netty.socket.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloServerHandler extends SimpleChannelInboundHandler<String> {

    /** 空闲次数 */
    private int idle_count =1;
    /** 发送次数 */
    private int count = 1;
    /**
     * 服务端处理客户端的核心方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
        public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 收到消息直接打印输出
        System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);
//        String message = String.valueOf(msg);
        String hard = msg.substring(0,4);
        if(hard.equals("AA75")){
            System.out.println("正在解析数据");

        }

        //如果是心跳命令，则发送给客户端;否则什么都不做
        if ("hb_request".equals(msg)) {
            ctx.writeAndFlush("服务端成功收到心跳信息");
            ctx.flush();
        }else{
            // 返回客户端消息 - 我已经接收到了你的消息
            ctx.writeAndFlush("Received your message !\n");
        }
    }


    protected HelloServerHandler() {
        super();
    }

    /**
     * 客户端与服务端创建链接的时候调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        super.channelActive(ctx);
        System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");

        ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");


    }


    /**
     * 客户端与服务端断开链接的时候调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    /**
     * 服务端接收客户端发送过来的数据结束之后调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 心跳超时处理逻辑
     * @param ctx
     * @param obj
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
            //如果读通道处于空闲状态，说明没有接收到心跳命令
            if (IdleState.READER_IDLE.equals(event.state())) {
                System.out.println("已经5秒没有接收到客户端的信息了");
                if (idle_count > 2) {
                    System.out.println("关闭这个不活跃的channel");
                    ctx.channel().close();
                }
                idle_count++;
            }
        } else {
            super.userEventTriggered(ctx, obj);
        }

    }

    /**
     * 工程出现异常的时候调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public static void main(String[] args) throws ParseException {
        String msg = "AA7500000000AABBCCDD011D58BCCE8A50F7EE3A100D193EA0014A00D27D009C400F78";
        String hard = msg.substring(0,4);
        if(hard.equals("AA75")){
            Long id = Long.parseLong(msg.substring(4,20),16);
            Long type = Long.parseLong(msg.substring(20,22),16);
            Long length = Long.parseLong(msg.substring(22,24),16);

            //时间
            Long time = Long.parseLong(msg.substring(24,32),16)*1000;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = simpleDateFormat.format(new Date(time));

            //经度   degrees = semicircles * ( 180 / 2^31 )
            Long longitude1 = Long.parseLong(msg.substring(32,40),16);
            long l = 1L << 31;
            double l1 = (double)180 / l;
            Double longitude = longitude1 * l1;
            //纬度
            Long latitude1 = Long.parseLong(msg.substring(40,48),16);
            Double latitude = latitude1 * l1;

            //心跳
            Long heartbeat = Long.parseLong(msg.substring(48,50),16);

            // 速度 0.01m/s
            Long speed1 = Long.parseLong(msg.substring(50,54),16);
            BigDecimal speed2 = new BigDecimal(speed1.floatValue()/100);
            BigDecimal speed=speed2.setScale(2,BigDecimal.ROUND_HALF_UP);

            //位置 正北为0 顺时针转 不能为负 例：210 = 南偏西30度
            Long position = Long.parseLong(msg.substring(54,58),16);

            //步频
            Long stepFreq = Long.parseLong(msg.substring(58,60),16);

            //距离 0.1m
            Long distance1 = Long.parseLong(msg.substring(60,66),16);
            BigDecimal distance2 = new BigDecimal(distance1.floatValue()/10);
            BigDecimal distance=distance2.setScale(1,BigDecimal.ROUND_HALF_UP);

        }
    }
}