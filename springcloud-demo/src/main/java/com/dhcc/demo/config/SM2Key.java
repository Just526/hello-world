package com.dhcc.demo.config;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class SM2Key {
    public static byte[] privateKeyByte;
    public static byte[] publicKeyByte;
    public static PrivateKey privateKey;
    public static PublicKey publicKey;
    static {
        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        privateKey = pair.getPrivate();
        publicKey = pair.getPublic();
        privateKeyByte=privateKey.getEncoded();
        publicKeyByte=publicKey.getEncoded();
        System.out.println("SM2 privateKey:"+ HexUtil.encodeHexStr(privateKeyByte));
        System.out.println("SM2 publicKey:"+ HexUtil.encodeHexStr(publicKeyByte));
    }
}
