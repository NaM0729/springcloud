package com.test.nettydemo.tcp.chat;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.omg.CORBA.OBJ_ADAPTER;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zyn
 * @Description
 * @date 2019-12-19 16:35
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(channelHandlerContext.channel().remoteAddress() + "开始发送数据：" + s);
        while (true) {
            for (Channel channel : channels) {
                channel.writeAndFlush(TransferDataByTest() + "\n");
            }
            Thread.sleep(1000);
        }
    }

    private String TransferDataByTest() {
        Map<String, Object> maps = new HashMap<>();

        maps.put("computerDate", "0天0时46分40秒");

        // cpu负荷，展示每秒的使用占比：23%
        Map<String, String> cpuMap = new HashMap<>();
        cpuMap.put("time", LocalTime.now().toString());
        cpuMap.put("value", "23");
        maps.put("cpu", cpuMap);
        // 硬盘信息：根目录和home目录的使用占比（各自均为100%） ：23% 、34%
        Map<String, String> hardMap = new HashMap<>();
        hardMap.put("root", "23");
        hardMap.put("home", "34");
        maps.put("disk", hardMap);
        // 内存状态 ：总计、未使用、使用、缓存、其他
        Map<String, String> memoryMap = new HashMap<>();
        memoryMap.put("total", "62.49GB");
        memoryMap.put("notUsed", "32.20GB");
        memoryMap.put("use", "21.32GB");
        memoryMap.put("cache", "8.96GB");
        memoryMap.put("other", "0KB");
        maps.put("memory", memoryMap);
        Map<String, String> networkMap = new HashMap<>();
        networkMap.put("date", LocalTime.now().toString());
        networkMap.put("rxkb", "73.68rxkB/s");
        networkMap.put("txkb", "73.68txkB/s");
        maps.put("network", networkMap);
        return JSONObject.toJSONString(maps);

    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel inComing = ctx.channel();
        for (Channel channel : channels) {
            if (channel != inComing)
                channel.writeAndFlush("[欢迎: " + inComing.remoteAddress() + "] 进入聊天室！\n");
        }
        channels.add(inComing);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel outComing = ctx.channel();
        for (Channel channel : channels) {
            if (channel != outComing)
                channel.writeAndFlush("[再见: ]" + outComing.remoteAddress() + " 离开聊天室！\n");
        }

        channels.remove(outComing);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线 \n");
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            if (channel != incoming) {
                System.out.println(incoming.remoteAddress() + "上线 \n");
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "下线 \n");
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.writeAndFlush(incoming.remoteAddress() + "下线 \n");
                System.out.println(incoming.remoteAddress() + "下线 \n");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        System.out.println(ctx.channel().remoteAddress() + "下线 \n");
    }
}
