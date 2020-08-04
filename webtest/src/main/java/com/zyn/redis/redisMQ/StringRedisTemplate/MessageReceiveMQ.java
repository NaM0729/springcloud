package com.zyn.redis.redisMQ.StringRedisTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zyn
 * @Description
 * @date 2020-07-24 14:24
 * <p>
 * 不是看到希望才会去坚持，而是坚持了才会看到希望
 */
@Component
public class MessageReceiveMQ {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

//    @PostConstruct
    public void doHandleMessage() {
        new Thread(() -> handMe()).start();
    }

    private void handMe() {
        while (true) {
            RedisConnection connection = stringRedisTemplate.getConnectionFactory().getConnection();
            List<byte[]> bytes = connection.bLPop(0, "syslog".getBytes());
            System.out.println("消息队列中的数据：" + bytes.get(0));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
