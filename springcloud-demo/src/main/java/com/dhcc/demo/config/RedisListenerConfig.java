package com.dhcc.demo.config;

import com.dhcc.demo.listener.RedisMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @description: redis监听配置
 * @author: guogang
 * @date: 2020/12/18 12:23
 * @version: 1.0
 */
@Configuration
public class RedisListenerConfig {
    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Autowired
    DefaultListableBeanFactory defaultListableBeanFactory;
    @Resource
    Map<String, RedisMessageListener> redisListeners;

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        if (!redisListeners.isEmpty()) {
            int index = 0;
            for (Map.Entry<String, RedisMessageListener> entry : redisListeners.entrySet()) {
                MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(entry.getValue());
                String beanName=MessageListenerAdapter.class.getSimpleName() + index++;
                defaultListableBeanFactory.registerSingleton(beanName, messageListenerAdapter);
                beanFactory.initializeBean(messageListenerAdapter,beanName);
                container.addMessageListener(messageListenerAdapter, new PatternTopic(entry.getKey()));
            }
        }
        return container;
    }
}
