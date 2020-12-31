package com.dhcc.demo.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 错误码定义缓存
 * @author: guogang
 * @date: 2020/12/18 15:44
 * @version: 1.0
 */
@Slf4j
//@Configuration
public class ErrorDefineCache {
    @Resource
    StringRedisTemplate stringredisTemplate;

    @Value("cbus-dmp:${spring.application.name}")
    private String serviceName;

    private static ErrorDefineCache errorDefineCache;


    private static HashMap<String, JSONObject> config = new HashMap<>();

    private JSONObject get(String errorCode) {
        return config.get(errorCode);
    }

    @PostConstruct
    public void init() {
        errorDefineCache = this;
        loadErrorDefine();
    }

    public void loadErrorDefine() {
        Map<String, String> entries = (Map) stringredisTemplate.opsForHash().entries(serviceName);
        entries.forEach((k, v) -> {
            JSONObject errorCode = JSONObject.parseObject(v);
            config.put(k, errorCode);
        });

    }

    public void refresh() {
        synchronized (this) {
            config.clear();
            loadErrorDefine();
        }
    }

    public static void refreshCache() {
        errorDefineCache.refresh();
    }
}
