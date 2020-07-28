package com.zyn.redisTemplate.publishSubscribe;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;


@Component
public class SendMessage {

    @Resource
    private StringRedisTemplate stringRedisTemplateAuto;
    private static StringRedisTemplate stringRedisTemplate;
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    @PostConstruct
    public void Int() {
        stringRedisTemplate = stringRedisTemplateAuto;
    }

    private static Integer count = 0;

    public void sendMess() {

        while (true) {
            for (int i = 0; i < 500; i++) {
                stringRedisTemplate.convertAndSend("testPubSub", LocalDateTime.now() + "发出消息:" + atomicInteger.getAndIncrement());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
