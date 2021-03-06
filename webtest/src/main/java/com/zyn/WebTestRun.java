package com.zyn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author htjd
 * @date 2018/11/15 9:20:19
 */
@EnableAsync
@SpringBootApplication
@EnableScheduling
@ServletComponentScan
public class WebTestRun {
    public static void main(String[] args) {
        SpringApplication.run(WebTestRun.class, args);

        // 发布/订阅
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.sendMess();

        // list做消息队列  开启SendMessageMQ、MessageReceiveMQ中的@PostConstruct
    }

}
