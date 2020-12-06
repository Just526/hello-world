package com.dhcc.demo.controller;

import com.dhcc.demo.model.CommonModel;
import com.dhcc.demo.service.secure.SecureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 安全接口
 * @author: guogang
 * @date: 2020/12/6
 * @version: 1.0
 */
@RestController
public class SecureController {
    @Autowired
    SecureService secureService;

    @PostMapping("sign")
    public String sign(@RequestBody CommonModel commonModel) {
        return secureService.sign(commonModel.getData().toString());
    }

    @PostMapping("verify")
    public boolean verify(@RequestBody CommonModel commonModel) {
        return secureService.verify(commonModel.getSign(),commonModel.getData().toString());
    }

    @PostMapping("encrypt")
    public String encrypt(@RequestBody CommonModel commonModel) {
        return secureService.sign(commonModel.getData().toString());
    }

    @PostMapping("signAndEncrypt")
    public String signAndEncrypt(@RequestBody  CommonModel commonModel) {
        return secureService.sign(commonModel.getData().toString());
    }
    @PostMapping("VerifyAndDecrypt")
    public String VerifyAndDecrypt(@RequestBody  CommonModel commonModel) {
        return secureService.sign(commonModel.getData().toString());
    }
}
