package com.dhcc.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.dhcc.demo.model.ErrorModel;
import com.dhcc.demo.model.StatisticModel;
import com.dhcc.demo.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @description: 安全接口
 * @author: guogang
 * @date: 2020/12/6
 * @version: 1.0
 */
@Slf4j
@RestController
//@EnableScheduling
public class RedisController {
    @Autowired
    StringRedisTemplate redisTemplate;

    private StatisticModel statisticModel = new StatisticModel();

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static final Long HOUR = TimeUnit.HOURS.toMillis(1);
    private static final Long HOUR6 = TimeUnit.HOURS.toMillis(6);
    private static final Long DAY = TimeUnit.DAYS.toMillis(1);
    private static final Long MINUTE10 =TimeUnit.MINUTES.toMillis(10);


    @PostMapping("set")
    public String set(@RequestBody String request) {
        Long start = System.currentTimeMillis();
        log.info("==>服务请求处理,请求参数[{}]",request);
        Long spend = System.currentTimeMillis() - start;
        statisticModel.count(spend);
        ErrorModel errorModel=new ErrorModel();
        errorModel.setServiceName("aaaaaaa");
        errorModel.setErrorCode("1000");
        errorModel.setErrorMsg("服务错误");
        errorModel.setSuccessFlag(false);
        errorModel.setRule("retCode");
        redisTemplate.convertAndSend("CBUS-DMP-CLIENT",JSONObject.toJSONString(errorModel));
        log.info("==>服务请求耗时：[{}]ms", spend);
        return request;
    }

    @PostMapping("error")
    public String error() {
       throw new IllegalArgumentException("模拟服务调用错误");
    }

    /**
     * @param request
     * @return
     */
    @PostMapping("send")
    public String send(@RequestBody String request) {
        redisTemplate.convertAndSend("CBUS-DMP-CLIENT",request);
        long end=System.currentTimeMillis();
        long start=end-HOUR;
        Set<String> strings = redisTemplate.opsForZSet().rangeByScore("service:zset:serviceName", start, end);

        List<String> objects = redisTemplate.opsForHash().multiGet("service:hset:serviceName", (Set)strings);
        Iterator<String> iterator = objects.iterator();
        long totalCount=0;
        long totalTime=0;
        while (iterator.hasNext()){
            String next = iterator.next();
            if(next!=null){
                JSONObject value = (JSONObject) JSONObject.parse(next);
                System.out.println(next+"--"+value);
                totalCount+=value.getLong("resCount");
                totalTime+=value.getLong("totalTime");
            }
        }
        BigDecimal avgTime=BigDecimal.ZERO;
        if(totalCount>0){
            avgTime= BigDecimal.valueOf(totalTime).divide(BigDecimal.valueOf(totalCount), 2, RoundingMode.HALF_UP);
        }
        System.out.println("totalCount"+":"+totalCount+"-- totalTime:"+totalTime+"-- avgTime:"+avgTime);
        return request;
    }

//    @Scheduled(cron = "0 0/1 * * * ?")
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
                    long successCount=tempStatisticModel.getResCount().longValue()-tempStatisticModel.getFailCount().longValue();
                    BigDecimal avgTime = BigDecimal.valueOf(tempStatisticModel.getTotalTime().longValue()).divide(BigDecimal.valueOf(tempStatisticModel.getResCount().longValue()), 2,BigDecimal.ROUND_HALF_UP);
                    BigDecimal successRatio = BigDecimal.valueOf(successCount).divide(BigDecimal.valueOf(tempStatisticModel.getResCount().longValue()), 4,BigDecimal.ROUND_HALF_UP);

                    tempStatisticModel.setSuccessCount(successCount);
                    tempStatisticModel.setAvgTime(avgTime);
                    tempStatisticModel.setSuccessRatio(successRatio);
                    tempStatisticModel.setMinute(DateUtils.getSimpleMinute());
                    if (log.isDebugEnabled()) {
                        log.info("本次统计次数搜集==>{}", tempStatisticModel);
                    }
                    String uuid = UUID.randomUUID().toString().replaceAll("-","");
                    redisTemplate.opsForHash().put("service:hset:serviceName", uuid, JSONObject.toJSONString(tempStatisticModel));
                    redisTemplate.opsForZSet().add("service:zset:serviceName",uuid,System.currentTimeMillis());
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
    @Scheduled(cron = " 0 0 23 * * ?")
    public void todayTask() {
        log.debug("=============自动任务删除七天前所有统计数据开始=============");
        Long nowTime = System.currentTimeMillis();
        Long spend =0L;
        try {
            // Zset获取7天前数据

            //删除 Hset 7天前数据

            //删除Zset 7天前数据
//            redisTemplate.opsForZSet().removeRangeByScore();

//            spend = nowTime - SEVEN;
//            pipeline.zremrangeByScore(serviceName,0,spend);
//            pipeline.zremrangeByScore(serviceName+"-"+ StateType.ERROR,0,spend);
//            pipeline.zremrangeByScore("serviceInvokeList",0,spend);
        } catch (Exception e) {
            log.error("redis数据清理任务处理异常！！！",e);
        }
    }

}
