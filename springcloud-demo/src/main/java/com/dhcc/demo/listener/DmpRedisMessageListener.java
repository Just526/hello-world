package com.dhcc.demo.listener;

import com.dhcc.demo.config.ErrorDefineCache;

//@Component("CBUS-DMP-SERVER")
public class DmpRedisMessageListener implements RedisMessageListener{

    @Override
    public void handleMessage(String message) {

        ErrorDefineCache.refreshCache();

        System.out.println(message);
    }
}
