package com.dhcc.demo.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HystrixController {
    private final Logger logger = LoggerFactory.getLogger(HystrixController.class);

    @HystrixCommand(fallbackMethod = "error")
    @GetMapping("/test")
    public Object test() {
//        String tempMsg = "{sign=f88898b2677e62f1ad54b9e330c0a27e, idcard=130333198901192762, realname=%E5%BE%90%E5%BD%A6%E5%A8%9C, key=c5d34d4c3c71cc45c88f32b4f13da887, mobile=13210141605, bankcard=6226430106137525}";
        String tempMsg = "\"userInfo:{}\", \"{n\" + \"            \"userName\":\"罗志祥\"，n\" + \"            \"idcard\":\"321183197701017846\",n\" + \"            \"password\":\"luozhixiang1234\",n\" + \"            \"mobile\":\"18888888888\"n\" + \"        }\"";
        logger.info("test[{}]",tempMsg);
        logger.info("mobile:{}", "13588889999");
//        throw new RuntimeException("exception");
       return  "hello world !";
    }

    @HystrixCommand(fallbackMethod = "error")
    @GetMapping("/test2")
    public Object test2() {
        logger.info("test2");
        return "hello world !";
    }

    public Object error(Throwable e) {
//        e.printStackTrace();
        System.out.println(Thread.currentThread().getName());
        return "error";
    }

}
