package com.dhcc.demo.service.secure;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.dhcc.demo.config.SM2Key;
import org.springframework.stereotype.Service;

/**
 * @description: 国密算法实现
 * @author: guogang
 * @date: 2020/12/6 3:33
 * @version: 1.0
 */
@Service
public class SMSecureService implements SecureService {

    @Override
    public String sign(String body) {
        return HexUtil.encodeHexStr(new SM2(SM2Key.ecPrivateKeyParameters,SM2Key.ecPublicKeyParameters).sign(body.getBytes()));
    }

    @Override
    public boolean verify(String sign, String body) {
        return new SM2(SM2Key.ecPrivateKeyParameters,SM2Key.ecPublicKeyParameters).verify(body.getBytes(), HexUtil.decodeHex(sign));
    }

    @Override
    public String encrypt(String secret,String body) {
        if(secret==null){
            return  new SM2(SM2Key.ecPrivateKeyParameters,SM2Key.ecPublicKeyParameters).encryptHex(body, KeyType.PublicKey);
        }
        return SmUtil.sm4(secret.getBytes()).encryptHex(body);
    }

    @Override
    public String decrypt(String secret, String body) {
        if(secret==null){
            return  new SM2(SM2Key.ecPrivateKeyParameters,SM2Key.ecPublicKeyParameters).decryptStr(body, KeyType.PrivateKey);
        }
        return SmUtil.sm4(secret.getBytes()).decryptStr(body, CharsetUtil.CHARSET_UTF_8);
    }

}
