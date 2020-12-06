package com.dhcc.demo.model;

/**
 * @description: 状态码 四位
 * @author: guogang
 * @date: 2020/12/6 13:46
 * @version: 1.0
 */
public  enum CommonStatus {

        FAIL(9999,"处理失败"),

        TIMEOUT(5555,"处理超时"),

        SUCCESS(0000,"处理成功");

        CommonStatus(int code, String message) {
            this.code = code;
            this.message = message;
        }

        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }