package com.dhcc.demo.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description: 统计数据模型
 * @author: guogang
 * @date: 2020/12/17 16:37
 * @version: 1.0
 */
public class StatisticModel {
    private AtomicLong resCount=new AtomicLong(0);
    private AtomicLong failCount=new AtomicLong(0);
    private AtomicLong totalTime=new AtomicLong(0);

    private BigDecimal avgTime;
    private BigDecimal successRatio;
    private long successCount;


    private int curConn;
    private String minute;

    private ConcurrentHashMap tecErr=new ConcurrentHashMap();
    private ConcurrentHashMap busErr=new ConcurrentHashMap();
    @JSONField(serialize=false)
    public boolean isCollect() {
        return this.getResCount().get() > 0;
    }
    /**
     * 统计计数
     */
    public void count(long speedTime) {
        resCount.incrementAndGet();
        totalTime.addAndGet(speedTime);
    }
    /**
     * 失败计数
     */
    public void fail(long speedTime) {
        count(speedTime);
        failCount.getAndIncrement();
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public AtomicLong getResCount() {
        return resCount;
    }

    public long getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(long successCount) {
        this.successCount = successCount;
    }

    public void setResCount(AtomicLong resCount) {
        this.resCount = resCount;
    }

    public AtomicLong getFailCount() {
        return failCount;
    }

    public void setFailCount(AtomicLong failCount) {
        this.failCount = failCount;
    }

    public BigDecimal getAvgTime() {
        return avgTime;
    }

    public AtomicLong getTotalTime() {
        return totalTime;
    }

    public BigDecimal getSuccessRatio() {
        return successRatio;
    }

    public void setSuccessRatio(BigDecimal successRatio) {
        this.successRatio = successRatio;
    }

    public void setTotalTime(AtomicLong totalTime) {
        this.totalTime = totalTime;
    }

    public void setAvgTime(BigDecimal avgTime) {
        this.avgTime = avgTime;
    }

    public int getCurConn() {
        return curConn;
    }

    public void setCurConn(int curConn) {
        this.curConn = curConn;
    }

    public ConcurrentHashMap getTecErr() {
        return tecErr;
    }

    public void setTecErr(ConcurrentHashMap tecErr) {
        this.tecErr = tecErr;
    }

    public ConcurrentHashMap getBusErr() {
        return busErr;
    }

    public void setBusErr(ConcurrentHashMap busErr) {
        this.busErr = busErr;
    }

    @Override
    public String toString() {
        return "StatisticModel{" +
                "resCount=" + resCount +
                ", failCount=" + failCount +
                ", avgTime=" + avgTime +
                ", totalTime=" + totalTime +
                ", curConn=" + curConn +
                ", tecErr=" + tecErr +
                ", busErr=" + busErr +
                '}';
    }
}
