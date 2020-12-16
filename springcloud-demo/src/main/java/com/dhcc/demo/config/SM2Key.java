package com.dhcc.demo.config;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.ECKeyUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
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

        ECPrivateKeyParameters ecPrivateKeyParameters2 = BCUtil.toParams(privateKey);
        final BigInteger d2 = ecPrivateKeyParameters2.getD();
        ECPublicKeyParameters ecPublicKeyParameters2 = BCUtil.toParams(publicKey);
        ECPoint q2 = ecPublicKeyParameters2.getQ();
        System.out.println("d2:"+ HexUtil.encodeHexStr(d2.toByteArray(),false));
        System.out.println("q2:"+HexUtil.encodeHexStr(q2.getEncoded(false),false));

//        SM2 sm2 = new SM2(ecPrivateKeyParameters2, null);
//        byte[] sign = sm2.sign("11111".getBytes());
//        System.out.println(HexUtil.encodeHexStr(sign));

//        sm2 = new SM2(null, ecPublicKeyParameters2);
//        String encrypt = sm2.encryptHex("11111".getBytes(), KeyType.PublicKey);
//        System.out.println(encrypt);
//
//        privateKeyByte=privateKey.getEncoded();
//        publicKeyByte=publicKey.getEncoded();
//
//        System.out.println("SM2 privateKey:"+ HexUtil.encodeHexStr(privateKeyByte));
//        System.out.println("SM2 publicKey:"+ HexUtil.encodeHexStr(publicKeyByte));

//        String d = "4BD9A450D7E68A5D7E08EB7A0BFA468FD3EB32B71126246E66249A73A9E4D44A";
//        String q = "04970AB36C3B870FBC04041087DB1BC36FB4C6E125B5EA406DB0EC3E2F80F0A55D8AFF28357A0BB215ADC2928BE76F1AFF869BF4C0A3852A78F3B827812C650AD3";

//        String d = "00E93E2220732CFE93F4EAA8D5B2B2E87EB5D89BF4750EE532C03E33E03FEDB1FF";
//        String q = "04AB2292022858BA2B0E9F905AE008697595F1F69C86EDB64CE70167CD7E00AC277F0558F0AA6EBFC01F929EE69BA50E1CA267673D4622670095427724ECFBB8BF";
        String d = "29118098DF2A37458F938EF32CAF4052925166655B7890F74650EC77CF3154AE";
        String q = "044016188A40823E723DAD5144E4A137DDCABC1E8775A0DB0033FD173697CCD706AA6ACAB3E799113103DB02E4FA339297551F51CB571973DACEF3CDD7E2FEBCCA";

        String data = "5c6c319dac175b89";

         ecPublicKeyParameters = ECKeyUtil.toSm2PublicParams(q);
         ecPrivateKeyParameters = ECKeyUtil.toSm2PrivateParams(d);




        final SM2 sm2 = new SM2(ecPrivateKeyParameters, ecPublicKeyParameters);
        sm2.setMode(SM2Engine.Mode.C1C2C3);
//        final String encryptHex = sm2.encryptHex(data, KeyType.PublicKey);
//        System.out.println(encryptHex);
//        final String decryptStr = sm2.decryptStr(encryptHex, KeyType.PrivateKey);
        final String decryptStr = sm2.decryptStr("049df53edf00d330b3fcb217b23f38fc2ce71b9ab55db65f3a6b2d154c31e157cd404564d19931e9373524d433fa41c58ce104d86804475d40ea055612ba40ed68d98c7296585409f9f7bc900e05e1bededd812e33ba102e2c0de0c04ddf12747d6c73bf363a67326af90d8c8d2a78ed70", KeyType.PrivateKey);
        System.out.println(decryptStr);

    }


    public static void main(String[] args) {

    }

}
