package com.dhcc.demo.listener;

/**
 * mq 消息监听处理器
 */
public interface RedisMessageListener {

    void handleMessage(String message);

}
