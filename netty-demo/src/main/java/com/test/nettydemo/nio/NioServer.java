package com.test.nettydemo.nio;

import com.sun.org.apache.bcel.internal.generic.Select;

import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author zyn
 * @Description nio demo
 * @date 2019-12-23 17:39
 */
public class NioServer {
    public static void main(String[] args) throws IOException {
        // 创建一个在本地端口进行监听的服务Socket通道.并设置为非阻塞方式
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //必须配置为非阻塞才能往selector上注册，否则会报错，selector模式本身就是非阻塞模式
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        // 创建一个选择器selector
        Selector selector = Selector.open();
        // 把ServerSocketChannel注册到selector上，并且selector对客户端accept连接操作感兴趣
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            System.out.println("等待事件发生");
            // 轮询监听channel里的key，select是阻塞的，accept()也是阻塞的
            int select = selector.select();

            System.out.println("有时间发生了");
            // 有客户端请求，被轮询监听到
            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            while (selectionKeyIterator.hasNext()){
                SelectionKey next = selectionKeyIterator.next();
                //删除本次已处理的key，防止下次select重复处理
                selectionKeyIterator.remove();
                handle(next);
            }
        }
    }

    /**
     * 处理逻辑
     * @param selectionKey
     */
    private static void handle(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isAcceptable()) {
            System.out.println("有客户端链接事件发生了");
            ServerSocketChannel socketChannel = (ServerSocketChannel) selectionKey.channel();
            //NIO非阻塞体现：此处accept方法是阻塞的，但是这里因为是发生了连接事件，所以这个方法会马上执行完，不会阻塞
            //处理完连接请求不会继续等待客户端的数据发送
            SocketChannel accept = socketChannel.accept();
            accept.configureBlocking(false);
            //通过Selector监听Channel时对读事件感兴趣
            accept.register(selectionKey.selector(),SelectionKey.OP_READ);
        }else if (selectionKey.isReadable()) {
            System.out.println("有客户端数据可读事件发生了。。");
            SocketChannel sc = (SocketChannel) selectionKey.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //NIO非阻塞体现:首先read方法不会阻塞，其次这种事件响应模型，当调用到read方法时肯定是发生了客户端发送数据的事件
            int len = sc.read(buffer);
            if (len != -1) {
                System.out.println("读取到客户端发送的数据：" + new String(buffer.array(), 0, len));
            }
            ByteBuffer bufferToWrite = ByteBuffer.wrap("HelloClient".getBytes());
            sc.write(bufferToWrite);
            selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        } else if (selectionKey.isWritable()) {
            SocketChannel sc = (SocketChannel) selectionKey.channel();
            System.out.println("write事件");
            // NIO事件触发是水平触发
            // 使用Java的NIO编程的时候，在没有数据可以往外写的时候要取消写事件，
            // 在有数据往外写的时候再注册写事件
            selectionKey.interestOps(SelectionKey.OP_READ);
            //sc.close();
        }
    }
}
