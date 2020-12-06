package com.dhcc.demo.model;

import lombok.Data;

import static com.dhcc.demo.model.CommonStatus.*;

/**
 * @description: 通用的bean 对象
 * @author: guogang
 * @date: 2020/12/6
 * @version: 1.0
 */
@Data
public class CommonModel {
    private String requestId; //uuid
    private String timestamp;
    private String sign;
    private String encryptKey; //加密密钥
    private String encryptAlg;  //加密算法
    private int code;
    private String message;
    private Object data;

    public CommonModel(Object data){
        this.data=data;
        this.code=SUCCESS.getCode();
        this.message=SUCCESS.getMessage();
    }
    public CommonModel(CommonStatus status){
        this.code=status.getCode();
        this.message=status.getMessage();
    }
    public CommonModel(int code,String  message){
        this.code=code;
        this.message=message;
    }
}
