package com.zyn.tail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author zyn
 * @Description
 * @date 2020-09-18 11:03
 * <p>
 * 不是看到希望才会去坚持，而是坚持了才会看到希望
 */
@Slf4j
@Component
public class TailerUtil {

    //    public static String LEASESFILE = "D:\\test\\dhcp\\dhcpd.leases";
    public static String LEASESFILE = "/var/lib/dhcpd/dhcpd.leases";


    public static void main(String[] args) {
        TailerListener listener = new TailerListenerAdapter() {

            @Override
            public void fileRotated() {
                log.info("fileRotated");
            }

            @Override
            public void handle(String line) {
                if (line.contains("lease")) {
                    log.info("写入的数据：{}" + line);
                }
            }
        };
        Tailer tailer = new Tailer(new File(LEASESFILE), listener, 1000*10, true);
        tailer.run();
        while (true) {
            System.out.println("1");
            try {
                Thread.sleep(1000 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
