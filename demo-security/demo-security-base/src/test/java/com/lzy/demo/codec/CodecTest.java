package com.lzy.demo.codec;

import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CodecTest {

    /**
     * md5, 128位, 16字节, 32位hex
     *
     * @throws Exception exception
     */
    @Test
    public void testMD5() throws Exception {
        byte[] bytes = "hello world".getBytes();

        MessageDigest messageDigest = MessageDigest.getInstance("md5");
        messageDigest.update(bytes);
        // 获得md5的数组
        byte[] md5Bytes = messageDigest.digest();

        // md5的数组长度为16个字节
        assertEquals(16, md5Bytes.length);

        // 将数组转为BigInteger, 1表示此数组为正数
        BigInteger bi = new BigInteger(1, md5Bytes);
        // 转为hex, 这种效率并不高, 参考spring和apache的方法
        // 两位hex可以表示一个字节, 因此md5转成hex就是32位
        // 格式化为%032x,表示32位的16进制, 不足补0, 用X表示大写
        String hex = String.format("%0" + (md5Bytes.length << 1) + "x", bi);

        // 使用spring的工具类
        String hexBySpring = org.springframework.util.DigestUtils.md5DigestAsHex(bytes);
        // 使用apache的工具类
        String hexByApache = org.apache.commons.codec.digest.DigestUtils.md5Hex(bytes);

        System.out.println("hex: " + hex);
        System.out.println("hexBySpring: " + hexBySpring);
        System.out.println("hexByApache: " + hexByApache);
        assertEquals(hex, hexBySpring);
        assertEquals(hex, hexByApache);
    }

    /**
     * sha256, 256位, 32字节, 64位hex
     *
     * @throws Exception exception
     */
    @Test
    public void testSHA256() throws Exception {
        byte[] bytes = "hello world".getBytes();

        MessageDigest messageDigest = MessageDigest.getInstance("sha256");
        messageDigest.update(bytes);
        // 获得sha256的数组
        byte[] sha256Bytes = messageDigest.digest();

        // sha256的数组长度为32个字节
        assertEquals(32, sha256Bytes.length);

        // 将数组转为BigInteger, 1表示此数组为正数
        BigInteger bi = new BigInteger(1, sha256Bytes);
        // 转为hex, 这种效率并不高, 参考spring和apache的方法
        // 两位hex可以表示一个字节, 因此sha256转成hex就是64位
        // 格式化为%064x,表示64位的16进制, 不足补0, 用X表示大写
        String hex = String.format("%0" + (sha256Bytes.length << 1) + "x", bi);

        // 使用apache的工具类
        String hexByApache = org.apache.commons.codec.digest.DigestUtils.sha256Hex(bytes);

        System.out.println("hex: " + hex);
        System.out.println("hexByApache: " + hexByApache);
        assertEquals(hex, hexByApache);
    }


    /**
     * HMAC(Hash-based Message Authentication Code, 密钥相关的哈希运算消息认证码), 将密钥和hash结合得消息认证码
     * 用来保证数据的完整性和身份验证(只用hash算法的话, hash值也可能被重新生成然后篡改)
     * HMAC可以使用多种hash算法, 比如HmacSHA256,HmacSHA512,HmacMD5
     * 为什么不直接使用key+message, 然后再去计算hash值？
     * 这样会有长度扩展攻击, 虽然不知道密钥, 但是可以在原始消息中附加消息然后计算出合法的hash值
     *
     * @throws Exception exception
     */
    @Test
    public void testHMAC() throws Exception {
        byte[] bytes = "hello world".getBytes();
        byte[] key = "12345678".getBytes();

        String algorithm = "HmacSHA256";
        SecretKeySpec signingKey = new SecretKeySpec(key, algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(signingKey);
        byte[] macBytes = mac.doFinal(bytes);
        String hex = Hex.encodeHexString(macBytes);
        System.out.println("hmac hex: " + hex);
    }

    /**
     * base64编码, 解码
     */
    @Test
    public void testBASE64() {
        String text = "hello world";
        byte[] bytes = text.getBytes();

        // encodeToString: 编码成字符串, encode: 编码成数组
        String encodedText = Base64.getEncoder().encodeToString(bytes);
        System.out.println("encodedText: " + encodedText);
        // 解码为字符数组
        byte[] decodedBytes = Base64.getDecoder().decode(encodedText);
        assertArrayEquals(bytes, decodedBytes);
    }
}
