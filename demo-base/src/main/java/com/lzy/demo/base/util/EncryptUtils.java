/*
 * Created by LZY on 2017/7/12 22:26.
 */
package com.lzy.demo.base.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

/**
 * 加密解密 工具类
 *
 * @author LZY
 * @version v1.0
 */
public class EncryptUtils {
    private static final String DES_ALGORITHM = "DES";

    /**
     * DES加密
     *
     * @param clearTest 明文
     * @param passwrod  密码
     * @return 密文 string
     * @throws Exception the exception
     */
    public static String desEncrypt(String clearTest, String passwrod) throws Exception {
        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, getKey(passwrod));
        byte[] dest = cipher.doFinal(clearTest.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(dest);
    }

    /**
     * DES解密
     *
     * @param cipherTest 密文
     * @param password   密码
     * @return 明文 string
     * @throws Exception the exception
     */
    public static String desDecrypt(String cipherTest, String password) throws Exception {
        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, getKey(password));
        byte[] dest = cipher.doFinal(Base64.getDecoder().decode(cipherTest));
        return new String(dest, StandardCharsets.UTF_8);
    }

    private static Key getKey(String key) throws Exception {
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
        return secretKeyFactory.generateSecret(new SecretKeySpec((key).getBytes(StandardCharsets.UTF_8), DES_ALGORITHM));
    }
}
