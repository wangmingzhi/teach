package com.teachpmp.server.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RSA {
    private static final Logger logger = LoggerFactory.getLogger(RSA.class);

    public static Map<String, KeyPairs> KEY_CONTAINER = new ConcurrentHashMap<>();

    public static final String CHARSET = "UTF-8";
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;


    public static KeyPairs genKeyPair() {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            logger.error("生成RSA密钥对出错", e);
            return null;
        }
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        KeyPairs keyPairs = new KeyPairs();
        keyPairs.setPrivateKey(privateKeyString);
        keyPairs.setPublicKey(publicKeyString);
        return keyPairs;
    }


    public static PublicKey getPublicKey(String publicKey) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            return factory.generatePublic(x509EncodedKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("getPublicKey出错", e);
        }
        return null;
    }

    public static PrivateKey getPrivateKey(String privateKey) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            return factory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            logger.error("getPrivateKey出错", e);
        }
        return null;
    }


    public static String publicEncrypt(String plainText, String publicKey) {
        byte[] b = plainText.getBytes();
        try {
            int inputLen = b.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(b, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(b, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return Base64.encodeBase64String(decryptedData);
        } catch (Exception e) {
            logger.error("publicEncrypt出错", e);
        }
        return null;
    }

    public static String privateDecrypt(String encodedText, String privateKey) {
        try {
            byte[] b = Base64.decodeBase64(encodedText);
            int inputLen = b.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(b, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(b, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData);
        } catch (Exception e) {
            logger.error("privateDecrypt出错", e);
        }
        return null;
    }


    public static String privateEncrypt(String plainText, String privateKey) {
        byte[] b = plainText.getBytes();
        try {
            int inputLen = b.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey(privateKey));
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(b, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(b, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return Base64.encodeBase64String(decryptedData);
        } catch (Exception e) {
            logger.error("privateEncrypt出错", e);
        }
        return null;
    }

    public static String publicDecrypt(String encodedText, String publicKey) {
        try {
            byte[] b = Base64.decodeBase64(encodedText);
            int inputLen = b.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getPublicKey(publicKey));
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(b, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(b, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData);
        } catch (Exception e) {
            logger.error("publicDecrypt出错", e);
        }
        return null;
    }


    /**
     * @Title: getRSAPrivateKeyAsNetFormat
     * @Description: 将Java私钥转化为.Net格式
     * @param privateKey
     * @return String
     */
    public static String getRSAPrivateKeyAsNetFormat(String privateKey) {
        try {
            StringBuffer buff = new StringBuffer(1024);

            PKCS8EncodedKeySpec pvkKeySpec = new PKCS8EncodedKeySpec(getPrivateKey(privateKey).getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateCrtKey pvkKey = (RSAPrivateCrtKey) keyFactory.generatePrivate(pvkKeySpec);

            buff.append("<RSAKeyValue>");
            buff.append(
                    "<Modulus>" + Base64.encodeBase64String(removeMSZero(pvkKey.getModulus().toByteArray())) + "</Modulus>");
            buff.append("<Exponent>" + Base64.encodeBase64String(removeMSZero(pvkKey.getPublicExponent().toByteArray()))
                    + "</Exponent>");
            buff.append("<P>" + Base64.encodeBase64String(removeMSZero(pvkKey.getPrimeP().toByteArray())) + "</P>");
            buff.append("<Q>" + Base64.encodeBase64String(removeMSZero(pvkKey.getPrimeQ().toByteArray())) + "</Q>");
            buff.append("<DP>" + Base64.encodeBase64String(removeMSZero(pvkKey.getPrimeExponentP().toByteArray())) + "</DP>");
            buff.append("<DQ>" + Base64.encodeBase64String(removeMSZero(pvkKey.getPrimeExponentQ().toByteArray())) + "</DQ>");
            buff.append("<InverseQ>" + Base64.encodeBase64String(removeMSZero(pvkKey.getCrtCoefficient().toByteArray()))
                    + "</InverseQ>");
            buff.append("<D>" + Base64.encodeBase64String(removeMSZero(pvkKey.getPrivateExponent().toByteArray())) + "</D>");
            buff.append("</RSAKeyValue>");
            return buff.toString();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    /**
     * @Title: getRSAPublicKeyAsNetFormat
     * @Description: 将Java公钥转化为.Net格式
     * @param encodedPublicKey
     * @return String
     */
    public static Map<String, String> getRSAPublicKeyAsNetFormat(String publicKey) {
        Map<String, String> map = new HashMap<>();
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey pukKey = (RSAPublicKey) keyFactory
                    .generatePublic(new X509EncodedKeySpec(getPublicKey(publicKey).getEncoded()));
//            StringBuffer buff = new StringBuffer(1024);
//            buff.append("<RSAKeyValue>");
//            buff.append(
//                    "<Modulus>" + Base64.encodeBase64String(removeMSZero(pukKey.getModulus().toByteArray())) + "</Modulus>");
//            buff.append("<Exponent>" + Base64.encodeBase64String(removeMSZero(pukKey.getPublicExponent().toByteArray()))
//                    + "</Exponent>");
//            buff.append("</RSAKeyValue>");
            map.put("a", Base64.encodeBase64String(removeMSZero(pukKey.getModulus().toByteArray())));
            map.put("m", Base64.encodeBase64String(removeMSZero(pukKey.getPublicExponent().toByteArray())));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * @Title: removeMSZero
     * @Description: JAVA和.Net密钥转化算法
     * @param data
     * @return byte[]
     */
    private static byte[] removeMSZero(byte[] data) {
        byte[] temp;
        int len = data.length;
        if (data[0] == 0) {
            temp = new byte[data.length - 1];
            System.arraycopy(data, 1, temp, 0, len - 1);
        } else
            temp = data;

        return temp;
    }


    public static void main(String[] args) {
        KeyPairs keyPairs = genKeyPair();
        StringBuffer buffer = new StringBuffer();
        String test = "[{\"id\":\"fc096cd7f15245399ff2fae479e97f00\",\"usercookie\":\"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\"}]\n";

        for (int i = 0; i<10; i++){
            buffer.append(test);
        }

        System.out.println("公钥："+getRSAPublicKeyAsNetFormat(keyPairs.getPublicKey()));

        String s1 = publicEncrypt(test, keyPairs.getPublicKey());
        String s2 = privateDecrypt(s1, keyPairs.getPrivateKey());
        System.out.println("公钥加密结果："+s1);
        System.out.println(s2);
        System.out.println("===================");

        long start = System.currentTimeMillis();
        String s3 = privateEncrypt(buffer.toString(), keyPairs.getPrivateKey());
        System.out.println(System.currentTimeMillis() - start);

        String s4 = publicDecrypt(s3, keyPairs.getPublicKey());
        System.out.println("私钥加密："+ s3.length());  //477356  47788
        System.out.println("===================");
//        System.out.println(s4);

        System.out.println();



    }



    public static class KeyPairs{
        private String privateKey;
        private String publicKey;

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }
    }
}
