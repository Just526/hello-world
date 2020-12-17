package com.dhcc.demo.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具类
 *
 * @author guo_gang
 */
public class DateUtils {

    /**
     * @Description:获取 格式为yyyyMMdd的日期
     */
    public static String getSimpleDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * @Description: 获取 格式为HHmmss
     */
    public static String getSimpleTime() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
    }

    /**
     * @Description: 获取 格式为HHmmssSSS
     */
    public static String getSimpleTimestamp() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmssSSS"));
    }

    /**
     * @Description: 获取格式为yyyyMMddHHmmss
     */
    public static String getSimpleDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    /**
     * @Description: 获取 标准格式为yyyy-MM-dd HH:mm:ss
     */
    public static String getStdDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * @Description: 获取 分钟格式为yyyyMMddHHmm
     */

    public static String getSimpleMinute() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
    }

}
