package com.lzy.demo.jwt;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.junit.jupiter.api.Test;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JWK(JSON Web Key),将公钥保存为json格式
 *
 * @author lzy
 * @version v1.0
 */
public class JWKTest {

    /**
     * Certificate和JWK
     *
     * @throws Exception exception
     */
    @Test
    public void testCertificateAndJWK() throws Exception {
        CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
        Certificate certificate = certificatefactory.generateCertificate(getClass().getResourceAsStream("/demo-java-cert.pem"));
        // 转为JKW
        JWK jwk = JWK.parse((X509Certificate) certificate);
        System.out.println(jwk);

        // 解析JWK
        JWK parseJWK = JWK.parse(jwk.toString());
        assertEquals(parseJWK, jwk);

        // 从JWK获取publicKey
        PublicKey parsePublicKey = parseJWK.toRSAKey().toRSAPublicKey();
        assertEquals(parsePublicKey, certificate.getPublicKey());
    }

    /**
     * jks和jwk(可以选择是否包含私钥)
     *
     * @throws Exception exception
     */
    @Test
    public void testJKSAndJWK() throws Exception {
        char[] password = "123456".toCharArray();
        KeyStore store = KeyStore.getInstance("JKS");
        store.load(getClass().getResourceAsStream("/demo-java.jks"), password);
        RSAPrivateCrtKey key = (RSAPrivateCrtKey) store.getKey("demo-java", password);
        RSAPublicKeySpec spec = new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);

        KeyPair keyPair = new KeyPair(publicKey, key);
        JWK jwk = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                // 包含私钥
                .privateKey(keyPair.getPrivate())
                .keyUse(KeyUse.SIGNATURE)
                .keyID(UUID.randomUUID().toString())
                .build();
        System.out.println(jwk);

        // 解析JWK
        JWK parseJWK = JWK.parse(jwk.toString());
        assertEquals(parseJWK, jwk);

        // 从JWK获取KeyPair
        KeyPair parseKeyPair = parseJWK.toRSAKey().toKeyPair();
        assertEquals(parseKeyPair.getPrivate(), keyPair.getPrivate());
        assertEquals(parseKeyPair.getPublic(), keyPair.getPublic());
    }
}
