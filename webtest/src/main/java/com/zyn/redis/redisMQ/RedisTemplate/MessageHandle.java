package com.zyn.redis.redisMQ.RedisTemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author zyn
 * @Description redis做消息队列
 * @date 2020-07-28 16:22
 * <p>
 * 不是看到希望才会去坚持，而是坚持了才会看到希望
 */
@Component
@Slf4j
public class MessageHandle {

    @Autowired
    RedisTemplate redisTemplate;

    static RedisTemplate redisTemplateStatic;

    static String KEY = "test";

    @PostConstruct
    public void init() {
        redisTemplateStatic = redisTemplate;
    }

    public static void dataToRedis(String message) {
        redisTemplateStatic.opsForList().leftPush(KEY, message);
        log.info("存入redis数据：{}", message);
    }

    public static void startRedisToSql() {
        new Thread(() -> redisToSql()).start();
    }

    private static void redisToSql() {
        while (true) {
            Object test = redisTemplateStatic.opsForList().rightPop(KEY, 0, TimeUnit.SECONDS);
            String deserialize = test.toString();
            log.info("获取redis数据：{}", deserialize);
        }
    }
}
