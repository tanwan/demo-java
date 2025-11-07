package com.lzy.demo.crypto;

import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.RSAPublicKeySpec;
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
        // iv是初始向量, CBC要求iv长度为加密算法的块大小
        // 作用是让相同的明文加密后有不同的密文
        // iv并不需要保密, 可以附加到密文上的, 这边是直接使用相同的IvParameterSpec
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        AlgorithmParameterSpec params = new IvParameterSpec(iv);

        // 加密
        String encryptedText = encrypt(cipher, secretKeySpec, params, plainText);

        // 解密
        String decryptedText = decrypt(cipher, secretKeySpec, params, encryptedText);

        assertEquals(plainText, decryptedText);
    }

    /**
     * CipherMode和Padding
     * 获取Cipher实例时传入的transformation一般由三个部分组成: AES/ECB/NoPadding
     * AES: 加密算法
     * CipherMode: 默认为ECB
     *    ECB(The Electronic Codebook Mode): 将明文分割成块,然后分别对每个块进行独立的加密,不安全
     *    CBC(The Cipher Block Chaining Mode): 将前一块的密文跟当前块的明文进行异或,再进行加密, 第一块跟iv进行异或
     *    CTR(The Counter Mode): 不需要填充
     *    GCM(The Galois/Counter Mode): 不需要填充, 是一种AEAD(Authenticated Encryption with Associated Data)
     *                                  对称加密算法无法判断密钥是否正确, 因此密文可以使用任何密钥执行解密运算,得到的明文也就无法得知是否正确
     *                                  AEAD在单纯的加密算法之上, 还加入了验证, 因此可以判断解密过程是否正确
     *    CFB(The Cipher Feedback Mode):
     *    OFB(The Output Feedback Mode):
     * Padding: 默认为PKCS5Padding, See com.sun.crypto.provider.CipherCore
     *    NoPadding: 不进行填充, 要求明文是加密算法分块大小的倍数
     *    PKCS5Padding: 填充的每个字节的值都是缺的字节数,只支持8字节的填充
     *                  比如:[0x41]=>[0x41,0x07,0x07,0x07,0x07,0x07,0x07,0x07]
     *                  特别的是当明文已经是8字节的整数倍时, 也还是需要进行填充, 填充内容为8个0x08, 为了解决判断解密后的原文的末尾是否是填充数据
     *    PKCS7Padding: 跟PKCS5Padding一样,支持1-255字节的填充, 不过JDK还不支持PKCS7Padding
     *
     * @throws Exception exception
     */
    @Test
    public void testCipherModePadding() throws Exception {
        // 明文需要是加密算法分块大小的倍数,不符合需要手动填充
        String plainText = "1234567812345678";
        byte[] password = "1234567812345678".getBytes();
        SecretKeySpec key = new SecretKeySpec(password, "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");

        // 加密
        String encryptedText = encrypt(cipher, key, null, plainText);

        // 解密
        String decryptedText = decrypt(cipher, key, null, encryptedText);

        assertEquals(plainText, decryptedText);
    }

    /**
     * AES的GCM(Galois/Counter Mode)模式
     * G指的是GMAC(Galois Message Authentication Code),使用Galois Field来计算消息的MAC值
     * C指的是CTR(分组加解密的一种模式)
     * 因此GCM不止可以加密,还有MAC的认证功能,可以保证密文的完整性
     *
     * @throws Exception exception
     */
    @Test
    public void testAESGCM() throws Exception {
        String plainText = "hello world";
        // 密码128位(AES128,16字节),192位(AES192,24字节)或256位(AES256,32字节)
        byte[] password = "1234567812345678".getBytes();
        SecretKeySpec key = new SecretKeySpec(password, "AES");
        // See CipherTest#testCipherModePadding()
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        // iv是初始向量, GCM模式长度推荐为12字节, 是随机生成的
        // 对于同一个cipher实例, gcm的iv是一次性的, 使用同一个cipher实例多次加密的话, iv都需要重新生成
        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);

        // tag的长度, 这边为128位(16字节)
        AlgorithmParameterSpec params = new GCMParameterSpec(128, iv);

        cipher.init(Cipher.ENCRYPT_MODE, key, params);

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        // 将iv添加到密文前面
        encryptedBytes = ByteBuffer.allocate(iv.length + encryptedBytes.length)
                .put(iv).put(encryptedBytes).array();

        // 密文的前12字节为iv
        params = new GCMParameterSpec(128, encryptedBytes, 0, 12);

        cipher.init(Cipher.DECRYPT_MODE, key, params);

        // 去掉前12位iv后, 才是真正的密文
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes, 12, encryptedBytes.length - 12);

        assertEquals(plainText, new String(decryptedBytes));
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
     * 使用RSA加解密
     *
     * @throws Exception exception
     */
    @Test
    public void testRSA() throws Exception {
        char[] password = "123456".toCharArray();
        String alias = "demo-java";
        KeyStore store = KeyStore.getInstance("JKS");
        store.load(getClass().getResourceAsStream("/demo-java.jks"), password);
        RSAPrivateCrtKey key = (RSAPrivateCrtKey) store.getKey(alias, password);
        RSAPublicKeySpec spec = new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);

        String plainText = "hello world";
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
        System.out.println("encryptedText: " + encryptedText);

        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        String decryptedText = new String(decryptedBytes);
        System.out.println("decryptedText: " + decryptedText);
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
        // CBC/GCM模式需要iv参数, ECB不需要
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
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedText = new String(decryptedBytes);
        System.out.println("decryptedText: " + decryptedText);
        return decryptedText;
    }
}
