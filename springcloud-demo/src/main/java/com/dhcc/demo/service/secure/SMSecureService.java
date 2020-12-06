package com.dhcc.demo.service.secure;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.symmetric.SM4;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
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
//        return SmUtil.sm2(SM2Key.privateKeyByte,SM2Key.publicKeyByte).signHex(HexUtil.encodeHexStr(body));
        return Base64.encode(SmUtil.sm2(SM2Key.privateKeyByte,SM2Key.publicKeyByte).sign(body.getBytes()));
    }

    @Override
    public boolean verify(String sign, String body) {
//        return SmUtil.sm2(SM2Key.privateKeyByte,SM2Key.publicKeyByte).verifyHex(HexUtil.encodeHexStr(body), sign);
        return SmUtil.sm2(SM2Key.privateKeyByte,SM2Key.publicKeyByte).verify(body.getBytes(),Base64.decode(sign));
    }

    @Override
    public String encrypt(String secret,String body) {
        return SmUtil.sm4(secret.getBytes()).encryptHex(body);
    }

    @Override
    public String decrypt(String secret, String body) {
        return SmUtil.sm4(secret.getBytes()).decryptStr(body, CharsetUtil.CHARSET_UTF_8);
    }
}
