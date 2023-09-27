package com.lzy.demo.crypto;

import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CryptoTest {

    /**
     * AES算法
     * 分组加密算法, 块大小为16字节: 分块后的每一块的明文和密文都是16字节, 因此,明文(包括填充部分)需要是16字节的倍数
     * AES128: 密钥: 128位(16字节), 加密轮次: 10轮
     * AES192: 密钥: 192位(24字节), 加密轮次: 12轮
     * AES256: 密钥: 256位(32字节), 加密轮次: 14轮
     *
     * @throws Exception exception
     * @see CryptoTest#testCipherModePadding()
     */
    @Test
    public void testAES() throws Exception {
        String plainText = "hello world";
        // 密码128位(AES128,16字节),192位(AES192,24字节)或256位(AES256,32字节)
        byte[] password = "1234567812345678".getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(password, "AES");
        // See CipherTest#testCipherModePadding()
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // CBC需要此参数, 数组长度为加密算法的块大小
        IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[16]);

        // 加密
        String encryptedText = encrypt(cipher, secretKeySpec, ivParameterSpec, plainText);

        // 解密
        String decryptedText = decrypt(cipher, secretKeySpec, ivParameterSpec, encryptedText);

        assertEquals(plainText, decryptedText);
    }


    /**
     * DES算法, 已经不再安全
     * 分组加密算法, 块大小为8字节: 分块后的每一块的明文和密文都是8字节, 因此,明文(包括填充部分)需要是8字节的倍数
     *
     * @throws Exception exception
     */
    @Test
    public void testDES() throws Exception {
        String plainText = "hello world";
        // 密钥只能是64位(8字节)
        byte[] password = "12345678".getBytes();
        // 密钥
        Key key = new SecretKeySpec(password, "DES");

        Cipher cipher = Cipher.getInstance("DES");

        // 加密
        String encryptedText = encrypt(cipher, key, null, plainText);

        // 解密
        String decryptedText = decrypt(cipher, key, null, encryptedText);

        assertEquals(plainText, decryptedText);
    }


    /**
     * 3DES(又称为DESede/Triple-DES/DES-EDE)算法
     * 本质还是DES算法, 对每个分组使用各自密钥进行3次DES加密, 因此它的密钥长度是DES的3倍
     * 分组加密算法, 块大小为8字节:分块后的每一块的明文和密文都是8字节, 因此,明文(包括填充部分)需要是8字节的倍数
     *
     * @throws Exception exception
     */
    @Test
    public void test3DES() throws Exception {
        String plainText = "hello world";
        // 密码只能是192位(24字节), 分成三组密钥, 给3次DES使用
        byte[] password = "123456781234567812345678".getBytes();
        Key key = new SecretKeySpec(password, "DESede");

        Cipher cipher = Cipher.getInstance("DESede");

        // 加密模式
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
        System.out.println("encryptedText: " + encryptedText);

        // 解密模式
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        String decryptedText = new String(decryptedBytes);
        System.out.println("decryptedText: " + decryptedText);
        assertEquals(plainText, decryptedText);
    }

    /**
     * CipherMode和Padding
     *
     * @throws Exception exception
     */
    @Test
    public void testCipherModePadding() throws Exception {
        // 明文需要是8字节的倍数,不符合需要手动填充
        String plainText = "1234567812345678";
        byte[] password = "12345678".getBytes();
        Key key = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(password));

        // DES: 加密算法
        // CipherMode: 默认为ECB
        //   ECB: 将明文分割成块,然后分别对每个块进行独立的加密,不推荐使用, 可以使用CBC
        //   CBC: 将前一块的密文跟当前块的明文进行异或,再进行加密
        // Padding: 默认为PKCS5Padding, See com.sun.crypto.provider.CipherCore
        //   NoPadding: 不进行填充, 要求明文是8字节的倍数
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");

        // 加密
        String encryptedText = encrypt(cipher, key, null, plainText);

        // 解密
        String decryptedText = decrypt(cipher, key, null, encryptedText);

        assertEquals(plainText, decryptedText);
    }

    /**
     * 部分加解密
     *
     * @throws Exception exception
     */
    @Test
    public void testPartial() throws Exception {
        String plainText = "hello world";
        // 密码需要是128位,192位或256位
        byte[] password = "1234567812345678".getBytes();

        SecretKeySpec secretKeySpec = new SecretKeySpec(password, "AES");
        // See CipherTest#testCipherModePadding()
        Cipher cipher = Cipher.getInstance("AES");

        // 只对明文中部分数据进行加密
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = new byte[32];
        // 先对plainText中偏移为0, 长度为5(hello)进行加密, 结果放在encryptedBytes偏移为0的地方
        cipher.doFinal(plainText.getBytes(), 0, 5, encryptedBytes, 0);
        // 再对plainText中偏移为6, 长度为5(world)进行加密, 结果放在encryptedBytes偏移为16的地方
        cipher.doFinal(plainText.getBytes(), 6, 5, encryptedBytes, 16);
        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
        System.out.println("encryptedText: " + encryptedText);

        byte[] base64Decode = Base64.getDecoder().decode(encryptedText);
        // 只对密文中部分数据进行解密
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = new byte[10];
        // 先对密文中偏移为0, 长度为16进行解密, 结果放在decryptedBytes偏移为0的地方
        cipher.doFinal(base64Decode, 0, 16, decryptedBytes, 0);
        // 再对密文中偏移为16, 长度为16进行解密, 结果放在decryptedBytes偏移为5的地方
        cipher.doFinal(base64Decode, 16, 16, decryptedBytes, 5);
        String decryptedText = new String(decryptedBytes);
        System.out.println("decryptedText: " + decryptedText);
        assertEquals(plainText.substring(0, 5) + plainText.substring(6, 11), decryptedText);
    }

    private String encrypt(Cipher cipher, Key key, AlgorithmParameterSpec params, String plainText) throws Exception {
        // CBC模式需要此参数, ECB不需要, 可以不会给init方法
        cipher.init(Cipher.ENCRYPT_MODE, key, params);
        // doFinal支持部分加解密, see CipherTest#testPartial()
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        System.out.println("length:" + encryptedBytes.length);
        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
        System.out.println("encryptedText: " + encryptedText);
        return encryptedText;
    }

    private String decrypt(Cipher cipher, Key key, AlgorithmParameterSpec params, String encryptedText) throws Exception {
        // CBC模式需要此参数, ECB不需要, 可以不会给init方法
        cipher.init(Cipher.DECRYPT_MODE, key, params);
        // doFinal支持部分加解密, see CipherTest#testPartial()
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        String decryptedText = new String(decryptedBytes);
        System.out.println("decryptedText: " + decryptedText);
        return decryptedText;
    }
}
