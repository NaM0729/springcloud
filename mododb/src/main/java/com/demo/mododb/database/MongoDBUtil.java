package com.demo.mododb.database;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MongoDBUtil {

    @Resource
    private MongoTemplate mongoTemplate;
    public void saveValue(String key,String value){
//        mongoTemplate.
    }
}
