package com.lzy.demo.bouncycastle;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BouncyCastleTest {

    /**
     * 动态添加BouncyCastleProvider
     */
    @BeforeAll
    public static void beforeAll() {
        Security.addProvider(new BouncyCastleProvider());
    }

    @AfterAll
    public static void afterAll() {
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
    }

    /**
     * 测试CMS加密解密
     *
     * @throws Exception exception
     */
    @Test
    public void testCMSCrypto() throws Exception {
        // 从指定的Provide获取CertificateFactory
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
        // 获取x.509证书
        X509Certificate certificate = (X509Certificate) certFactory
                .generateCertificate(getClass().getResourceAsStream(("/demo-java.cer")));

        // 使用x.509证书加密
        String secretMessage = "12345678";
        byte[] encryptedData = CMCCrypto.encryptData(secretMessage.getBytes(), certificate);

        // 使用私钥解密
        char[] password = "123456".toCharArray();
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(getClass().getResourceAsStream("/demo-java-PKCS12.p12"), password);
        PrivateKey privateKey = (PrivateKey) keystore.getKey("demo-java", password);
        byte[] rawData = CMCCrypto.decryptData(encryptedData, privateKey);
        assertArrayEquals(secretMessage.getBytes(), rawData);

        // 签名
        byte[] signedData = CMCCrypto.signData(rawData, certificate, privateKey);

        // 验证签名
        boolean check = CMCCrypto.verifSignData(signedData);

        assertTrue(check);
    }

    /**
     * 测试RipeMD160 hash
     *
     * @throws Exception exception
     */
    @Test
    public void testRipeMD160() throws Exception {
        MessageDigest md = MessageDigest.getInstance("RipeMD160");
        String message = "12345678";
        md.update(message.getBytes());
        byte[] result = md.digest();
        System.out.println(new BigInteger(1, result).toString(16));
    }
}
