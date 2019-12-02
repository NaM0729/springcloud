package com.test.nettydemo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class RedisReceiver implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisReceiver.class);
//    private CountDownLatch latch;

//    @Autowired
//    public RedisReceiver(CountDownLatch latch) {
//        this.latch = latch;
//    }

    public void receiveMessage(String message) {
        LOGGER.info("Received <" + message + ">");
//        latch.countDown();
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {

    }
}