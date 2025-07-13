package com.Japp.journelApp.service;

import com.Japp.journelApp.api.response.Root;
import com.Japp.journelApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherSer {
    @Value("${weather.api.key}")
    private String apiKey;
    @Autowired
    private RestTemplate restTemplate ;
    @Autowired
    private AppCache appCache;
    @Autowired
    private RedisService redisService;
    public Root getWeather(String city){
        Root root = redisService.get("weather_of_"+city,Root.class);
        if(root != null){
            return root;
        }else {
            String finalAPI = appCache.appCache.get("weather_api").replace("<CITY>", city).replace("<Key>", apiKey);
            ResponseEntity<Root> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, Root.class);
            Root body = response.getBody();
            if(body != null){
                redisService.set("weather_of_"+city,body,300l);
            }
            return body;
        }
            }
    }

