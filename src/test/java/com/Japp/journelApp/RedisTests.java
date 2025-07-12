package com.Japp.journelApp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testSend(){
        redisTemplate.opsForValue().set("email","verma@gmail.com");
        Object email = redisTemplate.opsForValue().get("nature");
        System.out.println(email);
        int a = 1;

    }
}
