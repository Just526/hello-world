package com.dhcc.demo.controller;

import com.dhcc.demo.model.CommonModel;
import com.dhcc.demo.service.secure.SecureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        if("SM2".equalsIgnoreCase(commonModel.getEncryptAlg())){
            return secureService.encrypt(null,commonModel.getEncryptKey());
        }
        return secureService.encrypt(commonModel.getEncryptKey(),commonModel.getData().toString());
    }
    @PostMapping("decrypt")
    public String decrypt(@RequestBody CommonModel commonModel) {
        if("SM2".equalsIgnoreCase(commonModel.getEncryptAlg())){
            return secureService.decrypt(null,commonModel.getEncryptKey());
        }
        return secureService.decrypt(commonModel.getEncryptKey(),commonModel.getData().toString());
    }

    @PostMapping("signAndEncrypt")
    public CommonModel signAndEncrypt(@RequestBody  CommonModel commonModel) {

        String encrypt = secureService.encrypt(commonModel.getEncryptKey(), commonModel.getData().toString());
        String emcrypt2=secureService.encrypt(null,commonModel.getEncryptKey());

        commonModel.setEncryptKey(emcrypt2);
        commonModel.setData(encrypt);
        String sign=secureService.sign(commonModel.getData().toString());
        commonModel.setSign(sign);
        return commonModel;
    }
    @PostMapping("VerifyAndDecrypt")
    public CommonModel VerifyAndDecrypt(@RequestBody  CommonModel commonModel) {
        boolean verify = secureService.verify(commonModel.getSign(), commonModel.getData().toString());
        if(verify){
            String secret = secureService.decrypt(null, commonModel.getEncryptKey());
            String decrypt = secureService.decrypt(secret, commonModel.getData().toString());
            commonModel.setEncryptKey(secret);
            commonModel.setData(decrypt);
        }else{
            commonModel.setCode(500);
            commonModel.setMessage("验签失败");
        }
        return commonModel;
    }
    @PostMapping("all")
    public CommonModel all(@RequestBody  CommonModel commonModel) {
        CommonModel commonModel1 = VerifyAndDecrypt(commonModel);
        if(commonModel1.getCode()==0){
          commonModel1 = signAndEncrypt(commonModel1);
        }
        return commonModel1;
    }

}
