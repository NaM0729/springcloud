package com.demo.device_common.util;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@Component
public class RedisKit {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    // 主要key 的前缀
    public static String main_prefix = "dev_";
    // 历史数据key 的前缀
    public static String history_prefix = "history_";

    public void addStringToList(String key, String value ){
        ListOperations<String, String> stringStringListOperations = stringRedisTemplate.opsForList();
        stringStringListOperations.rightPush(key,value);
        // 删除所有空串
        stringStringListOperations.remove(key,0,"");
    }

    public String getStringByKey(String key){

        return stringRedisTemplate.opsForValue().get(key);
    }

    public void saveString(String key, String value) {

        stringRedisTemplate.opsForValue().set(key, value);
    }

    public boolean existKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    public boolean existAllLikeKeys(String key) {
        Set keys = stringRedisTemplate.keys(key + "*");
        return keys.size() > 0;
    }

    public Set<String> getAllLikeKeys(String key) {
        Set<String> sets = stringRedisTemplate.keys(key);
        return sets;
    }

}