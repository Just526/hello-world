package com.dhcc.demo.model;

import lombok.Data;

@Data
public class ErrorModel {
    private String traceNo;
    private String serviceName;
    private String errorCode;
    private String errorMsg;
    private String valueType;
    private String rule;
    private boolean successFlag;
}
