package com.dhcc.demo.listener;

import com.dhcc.demo.config.ErrorDefineCache;
import org.springframework.stereotype.Component;

@Component("CBUS-DMP")
public class DmpRedisMessageListener implements RedisMessageListener{

    @Override
    public void handleMessage(String message) {

        ErrorDefineCache.refreshCache();

        System.out.println(message);
    }
}
