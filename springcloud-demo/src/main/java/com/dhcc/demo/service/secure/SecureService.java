package com.dhcc.demo.service.secure;

import org.springframework.stereotype.Service;

/**
 * @description: 安全服务
 * @author: guogang
 * @date: 2020/12/6 3:16
 * @version: 1.0
 */

public interface SecureService {
    /**
     * 报文签名
     * @param body 签名报文
     * @return 签名值
     */
     String sign(String body);

    /**
     * 签名校验
     * @param sign  签名值
     * @param body 验签报文
     * @return
     */
     boolean verify(String sign,String body);

    /**
     * 报文加密
     * @param secret 加密密钥
     * @param body 待加密报文
     * @return  加密报文
     */
     String encrypt(String secret, String body);

    /**
     * 报文解密
     * @param secret 解密密钥
     * @param body   待解密报文
     * @return 解密后报文
     */
     String decrypt(String secret ,String body);


}
