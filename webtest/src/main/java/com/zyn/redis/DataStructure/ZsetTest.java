package com.zyn.redis.DataStructure;

import io.lettuce.core.models.role.RedisSentinelInstance;
import io.lettuce.core.output.CommandOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;

/**
 * @author zyn
 * @Description
 * @date 2020-07-28 17:48
 * <p>
 * 不是看到希望才会去坚持，而是坚持了才会看到希望
 */
@Component
@Slf4j
public class ZsetTest {
    @Autowired
    RedisTemplate redisTemplate;


    public void add(String value) {
        Set<DefaultTypedTuple> set = new LinkedHashSet<>();
        set.add(new DefaultTypedTuple(value, getNowTimeByMinute()));
        Long fifteenSize = redisTemplate.opsForZSet().size("0728:www:fifteen");
        if (fifteenSize == 0) {
            redisTemplate.expire("0728:www:fifteen", 24, TimeUnit.HOURS);
        }
        Long add = redisTemplate.opsForZSet().add("0728:www:fifteen", set);


        Long allSize = redisTemplate.opsForSet().size("0728:www:all");
        if (allSize == 0) {
            redisTemplate.expire("0728:www:all", 24, TimeUnit.HOURS);
        }
        redisTemplate.opsForSet().add("0728:www:all", "7,7,9");

        Long size = redisTemplate.opsForHash().size("0728:www");
        if (size == 0) {
            redisTemplate.expire("0728:www", 24, TimeUnit.HOURS);
        }
        redisTemplate.opsForHash().increment("0728:www", "browers", 0);
        redisTemplate.opsForHash().increment("0728:www", "ip", 0);
        redisTemplate.opsForHash().increment("0728:www", "pc", 0);
        redisTemplate.opsForHash().increment("0728:www", "mobile", 0);
        redisTemplate.opsForHash().increment("0728:www", "bytes", 0);

    }

    public void addAndExpire() {
//        redisTemplate.opsForValue().set("test", "test");
//        redisTemplate.expire("test", 24, TimeUnit.SECONDS);
        redisTemplate.opsForHash().put("testLong", "long", 22L);
    }

    public void getValue(){

        Long domain_ip = (Long) redisTemplate.opsForHash().get("0730:2408.84e4.542.31ae.1.2.c0d5.afa3", "domain_ip");
        System.out.println(domain_ip.toString());
    }

    public void cleanData() {
        String fifteenKey = "0729:fifteen";
        List<String> keys = keys(fifteenKey + "*");
        keys.stream().forEach(key -> {

            long count = redisTemplate.opsForZSet().zCard(key);
            ConvertingCursor scan = (ConvertingCursor) redisTemplate.opsForZSet().scan(key, ScanOptions.scanOptions().count(count).build());
            while (scan.hasNext()) {
                DefaultTypedTuple converter = (DefaultTypedTuple) scan.next();
                Double score = converter.getScore();
                if (getNowTimeByMinute() - score >= 15) {
                    redisTemplate.opsForZSet().remove(key, converter.getValue());
                }

            }
            try {
                scan.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void cleanData2() {
        String fifteenKey = "0729:fifteen";
        List<String> keys = keys(fifteenKey + "*");
        keys.stream().forEach(key -> {
            // 取key中集合大小
            Long aLong = redisTemplate.opsForZSet().zCard(key);
            // 取key中所有value
            Set<String> set = redisTemplate.opsForZSet().reverseRange(key, 0, aLong);
            set.stream().forEach(value -> {
                Double score = redisTemplate.opsForZSet().score(key, value);
                if (getNowTimeByMinute() - score >= 15) {
                    redisTemplate.opsForZSet().remove(key, value);
                }
            });
        });
    }

    /**
     * scan 实现
     *
     * @param pattern  表达式
     * @param consumer 对迭代到的key进行操作
     */
    public void scan(String pattern, Consumer<byte[]> consumer) {
        redisTemplate.execute((RedisConnection connection) -> {
            try (Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().count(Long.MAX_VALUE).match(pattern).build())) {
                cursor.forEachRemaining(consumer);
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 获取符合条件的key
     *
     * @param pattern 表达式
     * @return
     */
    public List<String> keys(String pattern) {
        List<String> keys = new ArrayList<>();
        this.scan(pattern, item -> {
            //符合条件的key
            String key = new String(item, StandardCharsets.UTF_8);
            keys.add(key);
        });
        return keys;
    }

    /**
     * 计算当前时间与0点的分钟差 将数据位数调小
     *
     * @return
     */
    private Double getNowTimeByMinute() {
        LocalDateTime localDateTime = LocalDateTime.now();
        long timestamp = localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        LocalDateTime localDateTime1 = LocalDateTime.of(2020, 07, 28, 0, 0, 0);
        long timestamp1 = localDateTime1.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        long diff = (timestamp - timestamp1) / 1000 / 60;
        return Double.valueOf(diff);
    }
}
