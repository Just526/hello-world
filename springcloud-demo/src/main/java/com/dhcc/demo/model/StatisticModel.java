package com.dhcc.demo.model;

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
    private volatile BigDecimal avgTime=new BigDecimal(0);
    private AtomicLong totalTime=new AtomicLong(0);
    private int curConn;
    private ConcurrentHashMap tecErr=new ConcurrentHashMap();
    private ConcurrentHashMap busErr=new ConcurrentHashMap();

    public boolean isCollect() {
        return this.getResCount().get() > 0;
    }



    /**
     * 统计计数
     */
    public void count(long speedTime) {
        final long curResCount = resCount.incrementAndGet();
        final long curTotalTime = totalTime.addAndGet(speedTime);
        //平均耗时与当前交易耗时一样，不用计算
        if (avgTime.equals(speedTime)) {
            return;
        }
    }
    /**
     * 失败计数
     */
    public void fail(long speedTime) {
        count(speedTime);
        failCount.getAndIncrement();
    }

    public AtomicLong getResCount() {
        return resCount;
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
                ", curConn=" + curConn +
                ", tecErr=" + tecErr +
                ", busErr=" + busErr +
                '}';
    }
}
