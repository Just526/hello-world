package com.dhcc.demo.test;

import org.junit.Test;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringPatternTest {
    @Test
    public void phonePatternTest() {
        String reg = "(.*)((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))(\\d{4})(\\d{4})(.*)";
        String replacement = "$1$3$4$5$6$7***$9$10";
        String message = "moble-14609233055-111";
        System.out.println(message);
        long start=System.currentTimeMillis();
        Matcher matcher = Pattern.compile(reg).matcher(message);
//        if(matcher.find()) {
//               System.out.println("group:"+matcher.group(4));
//        }
        System.out.println();
        message=matcher.replaceAll(replacement);
        System.out.println(message +" spend: " +(System.currentTimeMillis()-start));
    }
    @Test
    public void emailPatternTest() {
        String reg = "(.*)([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+([a-zA-Z]{2,})(.*)";
        String replacement = "$1***$3$4$5";
        String message = "moble-1803008589@qq.com-111";
        System.out.println(message);
        long start=System.currentTimeMillis();
        Matcher matcher = Pattern.compile(reg).matcher(message);

        if(matcher.find()) {
               System.out.println("group:"+matcher.group(1));
        }
        System.out.println(matcher.groupCount());
        message=matcher.replaceAll(replacement);
        System.out.println(message +" spend: " +(System.currentTimeMillis()-start));
    }
    /**
     * 身份证脱敏表达式
     */
    @Test
    public void idCardPatternTest() {
        String reg = "(.*)([1-9]\\d{5})[1-9]\\d{3}((0[1-9])|(1[0-2]))(0[1-9]|([1|2][0-9])|3[0-1])((\\d{4})|\\d{3}X)";
        String replacement = "$1$2********$8";
        String message = "moble-610524199406182012-111";
        System.out.println(message);
        long start=System.currentTimeMillis();
        Matcher matcher = Pattern.compile(reg).matcher(message);
        if(matcher.find()) {
               System.out.println("group:"+matcher.group(1));
        }
        System.out.println();
        message=matcher.replaceAll(replacement);
        System.out.println(message +" spend: " +(System.currentTimeMillis()-start));
    }

    /**
     * 账户脱敏表达式
     */
    @Test
    public void acctNoPatternTest() {
        String reg = "(.*)([1-9]\\d{3})(\\d{7}|\\d{11}|\\d{9})(\\d{4})(.*)";
        String replacement = "$1*********$4$5";
        String message = "moble-6228482916080803367-111";
        System.out.println(message);
        long start=System.currentTimeMillis();
        Matcher matcher = Pattern.compile(reg).matcher(message);
        if(matcher.find()) {
               System.out.println("group:"+matcher.group(1));
        }
        System.out.println();
        message=matcher.replaceAll(replacement);
        System.out.println(message +" spend: " +(System.currentTimeMillis()-start));
    }
}
