package com.Japp.journelApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key,Class<T> rootClass){
        try {
            Object obj = redisTemplate.opsForValue().get(key);
            if(obj == null){
                log.warn("Radis cache miss for key : {}",key);
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(obj.toString(),rootClass);
        }catch (Exception e){
            log.error("Error While Loading from Redis for key {} : ", key,e);
            return null;
        }
    }
    public void set(String key,Object obj,Long ttl){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonValue = objectMapper.writeValueAsString(obj);
            redisTemplate.opsForValue().set(key, jsonValue,ttl, TimeUnit.SECONDS);

        }catch (Exception e){
            log.error("Error While Loading... : ",e);

        }
    }
}
