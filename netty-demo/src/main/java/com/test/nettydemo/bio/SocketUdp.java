package com.test.nettydemo.bio;

import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author zyn
 * @Description  socket发送udp协议数据
 * @date 2020-02-28 10:15
 */
@Slf4j
public class SocketUdp {
    public static void main(String[] args) throws IOException {
        InetAddress inetAddress = InetAddress.getByName("127.0.0.1");

        while (true) {
            byte[] data = transfData().getBytes();
            log.info(transfData());
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length, inetAddress, 8888);
            DatagramSocket datagramSocket = new DatagramSocket();
            datagramSocket.send(datagramPacket);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static String transfData(){
        return "<190>Apr  9 10:14:00 localhost.localdomain nginx: 2408:84e4:542:31ae:1:2:c0d5:afa3" +
                ",www.weihai.gov.cn,[09/Apr/2020:10:14:00 +0800]" +
                ",GET /vc/vc/interface/artcount/artcount.jsp?i_columnid=45569&i_articleid=2240605 HTTP/1.1" +
                ",200,http://rsj.weihai.gov.cn/art/2019/12/25/art_45569_2240605.html" +
                ",Mozilla/5.0 (Linux; Android 9; PACM00 Build/PPR1.180610.011; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/70.0.3538.110 Mobile Safari/537.36 MMWEBID/8645 MicroMessenger/7.0.12.1620(0x27000C50) Process/tools NetType/4G Language/zh_CN ABI/arm64,-,-,56";
    }
}
