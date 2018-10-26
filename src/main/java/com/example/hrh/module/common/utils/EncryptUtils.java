package com.example.hrh.module.common.utils;/**
 * Created by Administrator on 2018/10/22 0022.
 */
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/22 0022 10:48
 */
public class EncryptUtils {


    public static final String BASE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String SALT = "I_AM_A_SALT";
    public static final String ENCRYPT_KEY = "encryptKey";
    /**
     * 算法
     */
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    private static EncryptUtils encryptUtils;
    private MessageDigest sha256;
    private Base64 base64;

    private EncryptUtils() throws NoSuchAlgorithmException {
        sha256 = MessageDigest.getInstance("SHA-256");
        base64 = new Base64();
    }

    public String encodeBase64(String source) {
        return base64.encodeAsString(source.getBytes());
    }

    public byte[] decodeBase64(String source) {
        return base64.decode(source);
    }


    /**
     * AES解密
     *
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey   解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);

        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));

        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    /**
     * AES加密
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));

        return cipher.doFinal(content.getBytes("utf-8"));
    }

    /**
     * 随机秘钥生成器
     *
     * @param KeyLength
     * @return
     */
    public static String KeyCreate(int KeyLength) {
        Random random = new Random();
        StringBuilder keySb = new StringBuilder();

        for (int i = 0; i < KeyLength; i++) {
            int number = random.nextInt(BASE.length());
            keySb.append(BASE.charAt(number));
        }
        return keySb.toString();
    }

    public String MD5Hex(String password) {
        return DigestUtils.md5Hex(password).toUpperCase();
    }

    public String hashSha256WithSalt(String password) {

        try {
            System.out.println(password);
            byte[] bytes = sha256.digest(password.getBytes());
            String source = Hex.encodeHexString(bytes) + SALT;
            bytes = sha256.digest(source.getBytes("UTF-8"));
            String result = Hex.encodeHexString(bytes);
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static EncryptUtils getInstance() {
        if (null == encryptUtils) {
            synchronized (EncryptUtils.class) {
                if (null == encryptUtils) {
                    try {
                        encryptUtils = new EncryptUtils();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return encryptUtils;
    }


}
