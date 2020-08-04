package com.zyn.redis.redisMQ.StringRedisTemplate;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author zyn
 * @Description
 * @date 2020-07-24 14:24
 * <p>
 * 不是看到希望才会去坚持，而是坚持了才会看到希望
 */
@Component
public class SendMessageMQ {
    @Resource
    StringRedisTemplate stringRedisTemplate;

//    @PostConstruct
    public void sendMess() {
        new Thread(() -> sendM()).start();

    }

    /**
     * 该方法存入数据时会出现存不进去问题。。未解决！！！！！！！！！！！！！
     */
    private void sendM() {
        while (true) {
            for (int i = 0; i < 500; i++) {
                RedisConnection connection = stringRedisTemplate.getConnectionFactory().getConnection();
                connection.rPush("syslog".getBytes(), LocalDateTime.now().toString().getBytes());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
