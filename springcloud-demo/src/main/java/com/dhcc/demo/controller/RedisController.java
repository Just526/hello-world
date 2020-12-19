package com.dhcc.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.dhcc.demo.model.StatisticModel;
import com.dhcc.demo.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description: 安全接口
 * @author: guogang
 * @date: 2020/12/6
 * @version: 1.0
 */
@Slf4j
@RestController
@EnableScheduling
public class RedisController {
    @Autowired
    StringRedisTemplate redisTemplate;

    private StatisticModel statisticModel = new StatisticModel();

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @PostMapping("set")
    public String set(@RequestBody String request) {
        Long start = System.currentTimeMillis();
        log.info("==>服务请求处理,请求参数[{}]",request);
        Long spend = System.currentTimeMillis() - start;
        statisticModel.count(spend);
        log.info("==>服务请求耗时：[{}]ms", spend);
        return request;
    }

    @PostMapping("send")
    public String send(@RequestBody String request) {
        redisTemplate.convertAndSend("CBUS-DMP",request);
        return request;
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void statistic() {
        synchronized (statisticModel) {
            //定义临时统计数据模型
            final StatisticModel tempStatisticModel = statisticModel;
            if (log.isDebugEnabled()) {
                log.debug("任务统计执行...");
            }
            if (tempStatisticModel.isCollect()) {
                executorService.execute(() -> {
                    //redis 存放相关信息
                    BigDecimal avgTime = BigDecimal.valueOf(tempStatisticModel.getTotalTime().longValue()).divide(BigDecimal.valueOf(tempStatisticModel.getResCount().longValue()), 2,BigDecimal.ROUND_HALF_UP);
                    tempStatisticModel.setAvgTime(avgTime);
                    if (log.isDebugEnabled()) {
                        log.info("本次统计次数搜集==>{}", tempStatisticModel);
                    }
                    redisTemplate.opsForHash().put("service:serviceName", DateUtils.getSimpleMinute(), JSONObject.toJSONString(tempStatisticModel));
                });
                //初始化对象
                this.statisticModel = new StatisticModel();
            } else {
                if (log.isDebugEnabled()) {
                    log.info("本次统计无数据请求");
                }
            }
        }
    }
}
