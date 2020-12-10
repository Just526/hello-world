package com.dhcc.demo.config;

import cn.hutool.crypto.ECKeyUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class SM2Key {
    public static byte[] privateKeyByte;
    public static byte[] publicKeyByte;
    public static PrivateKey privateKey;
    public static PublicKey publicKey;
    public static ECPublicKeyParameters ecPublicKeyParameters;
    public static ECPrivateKeyParameters ecPrivateKeyParameters;


    static {
        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        privateKey = pair.getPrivate();
        publicKey = pair.getPublic();
//        System.out.println(privateKey);
//        System.out.println(publicKey);
//
//        privateKeyByte=privateKey.getEncoded();
//        publicKeyByte=publicKey.getEncoded();
//
//        System.out.println("SM2 privateKey:"+ HexUtil.encodeHexStr(privateKeyByte));
//        System.out.println("SM2 publicKey:"+ HexUtil.encodeHexStr(publicKeyByte));

//        String d = "4BD9A450D7E68A5D7E08EB7A0BFA468FD3EB32B71126246E66249A73A9E4D44A";
//        String q = "04970AB36C3B870FBC04041087DB1BC36FB4C6E125B5EA406DB0EC3E2F80F0A55D8AFF28357A0BB215ADC2928BE76F1AFF869BF4C0A3852A78F3B827812C650AD3";

        String d = "00E93E2220732CFE93F4EAA8D5B2B2E87EB5D89BF4750EE532C03E33E03FEDB1FF";
        String q = "04AB2292022858BA2B0E9F905AE008697595F1F69C86EDB64CE70167CD7E00AC277F0558F0AA6EBFC01F929EE69BA50E1CA267673D4622670095427724ECFBB8BF";

        String data = "123456";

         ecPublicKeyParameters = ECKeyUtil.toSm2PublicParams(q);
         ecPrivateKeyParameters = ECKeyUtil.toSm2PrivateParams(d);

        final SM2 sm2 = new SM2(ecPrivateKeyParameters, ecPublicKeyParameters);
        final String encryptHex = sm2.encryptHex(data, KeyType.PublicKey);
        final String decryptStr = sm2.decryptStr(encryptHex, KeyType.PrivateKey);

        System.out.println(encryptHex);

        System.out.println(decryptStr);

    }


    public static void main(String[] args) {

    }

}
