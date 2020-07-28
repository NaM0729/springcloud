package com.zyn.redisTemplate.redisMQ;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.KeyspaceEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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

    @Resource
    StringRedisTemplate stringRedisTemplateAuto;
    static StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    public void Instance() {
        stringRedisTemplate = stringRedisTemplateAuto;
    }

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
