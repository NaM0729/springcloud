package com.zyn.redis.publishSubscribe;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author zyn
 * @Description syslog数据入库
 * @date 2020-07-22 16:43
 * <p>
 * 不是看到希望才会去坚持，而是坚持了才会看到希望
 */
@Service
public class MessageReceive{

    @Resource
    StringRedisTemplate stringRedisTemplateAuto;
    static StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    public void init() {
        stringRedisTemplate = stringRedisTemplateAuto;
    }


    public void saveMessage(String messageinfo) {

        System.out.println("testPubSub通道的订阅消息：" + messageinfo);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void saveMessage1(String messageinfo) {
        System.out.println("syslog通道的订阅消息：" + messageinfo);
    }


}
