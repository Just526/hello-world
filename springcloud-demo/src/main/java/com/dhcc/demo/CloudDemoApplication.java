package com.dhcc.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableHystrix
@MapperScan("com.dhcc.demo.module.*.mapper")
@SpringBootApplication
public class CloudDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudDemoApplication.class,args);
    }
}
