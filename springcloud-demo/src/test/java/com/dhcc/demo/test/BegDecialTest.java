package com.dhcc.demo.test;

import org.junit.Test;

import java.math.BigDecimal;

public class BegDecialTest {
    @Test
    public void  test(){
        BigDecimal a = BigDecimal.valueOf(1.9);
        BigDecimal divide = a.divide(BigDecimal.valueOf(20), 2, BigDecimal.ROUND_HALF_UP);
        System.out.println(divide);
    }
}
