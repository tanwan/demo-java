package com.lzy.demo.jwt;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JWTTest {

    /**
     * 使用hs256加密
     *
     * @throws Exception exception
     */
    @Test
    public void testJWTUseHS256() throws Exception {
        // HS256算法要求密码要大于256位
        byte[] password = "12345678901234567890123456789012".getBytes(StandardCharsets.UTF_8);

        Key key = new SecretKeySpec(password, SignatureAlgorithm.HS256.getJcaName());
        // 使用jjwt生成jwt
        String jwt = generateJWT(key);
        System.out.println(jwt);

        // 使用jose生成jwt
        String jwtUseJOSE = generateJWTUseJOSE(new MACSigner(password), JWSAlgorithm.HS256);
        System.out.println(jwtUseJOSE);

        // 使用jjwt解析jwt
        Claims parseClaims = parserJWT(jwt, key);
        System.out.println(parseClaims);

        // 使用jose解析jwt
        JWTClaimsSet parseClaimsUseJOSE = parserJWTUseJOSE(jwtUseJOSE, new MACVerifier(password));
        System.out.println(parseClaimsUseJOSE);
    }

    /**
     * 使用rsa加密 jks的密钥库
     * 在当前目录生成jks: keytool -genkey -alias demo-java -keyalg RSA -validity 36500  -keystore demo-java.jks
     * 导出公钥:         keytool -export -alias demo-java -keystore demo-java.jks -storepass 123456 -file  demo-java.cer
     *
     * @throws Exception exception
     */
    @Test
    public void testJWTUseRSAJKS() throws Exception {
        testJWTUseRSA("JKS", "/demo-java.jks");
    }

    /**
     * 使用rsa加密 pkcs12的密钥库
     * 将jks转为pkcs12  keytool -importkeystore -srckeystore demo-java.jks -destkeystore demo-java-pkcs12.p12 -deststoretype pkcs12
     * pkcs12也可以同jks使用相同的命令导出公钥
     * 使用openssl查看pkcs12 openssl pkcs12 -nokeys -info -in demo-java-pkcs12.p12 -passin pass:123456
     *
     * @throws Exception exception
     */
    @Test
    public void testJWTUseRSAPKCS12() throws Exception {
        testJWTUseRSA("PKCS12", "/demo-java-PKCS12.p12");
    }


    private void testJWTUseRSA(String keyStoreType, String keyStoreFile) throws Exception {
        char[] password = "123456".toCharArray();
        String alias = "demo-java";
        KeyStore store = KeyStore.getInstance(keyStoreType);
        store.load(getClass().getResourceAsStream(keyStoreFile), password);
        RSAPrivateCrtKey key = (RSAPrivateCrtKey) store.getKey(alias, password);
        RSAPublicKeySpec spec = new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);
        KeyPair keyPair = new KeyPair(publicKey, key);

        // 使用jjwt生成jwt
        String jwt = generateJWT(keyPair.getPrivate());
        System.out.println(jwt);

        // 使用jose生成jwt
        String jwtUseJOSE = generateJWTUseJOSE(new RSASSASigner(keyPair.getPrivate()), JWSAlgorithm.RS256);
        System.out.println(jwtUseJOSE);


        // 使用jks获取的公钥
        // 使用jjwt解析jwt
        Claims parseClaims = parserJWT(jwt, keyPair.getPublic());
        System.out.println(parseClaims);
        // 使用jose解析jwt
        JWTClaimsSet parseClaimsUseJOSE = parserJWTUseJOSE(jwtUseJOSE, new RSASSAVerifier((RSAPublicKey) keyPair.getPublic()));
        System.out.println(parseClaimsUseJOSE);


        // 使用cer文件获取公钥
        CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
        Certificate cert = certificatefactory.generateCertificate(getClass().getResourceAsStream("/demo-java.cer"));
        publicKey = cert.getPublicKey();
        String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        publicKeyStr = "-----BEGIN PUBLIC KEY-----\n" + publicKeyStr + "\n-----END PUBLIC KEY-----";
        System.out.println(publicKeyStr);

        // 使用jjwt解析jwt
        parseClaims = parserJWT(jwt, publicKey);
        System.out.println(parseClaims);
        // 使用jose解析jwt
        parseClaimsUseJOSE = parserJWTUseJOSE(jwtUseJOSE, new RSASSAVerifier((RSAPublicKey) publicKey));
        System.out.println(parseClaimsUseJOSE);

        // 使用pem文件获取公钥
        certificatefactory = CertificateFactory.getInstance("X.509");
        cert = certificatefactory.generateCertificate(getClass().getResourceAsStream("/demo-java-cert.pem"));
        publicKey = cert.getPublicKey();

        // 使用jjwt解析jwt
        parseClaims = parserJWT(jwt, publicKey);
        System.out.println(parseClaims);
        // 使用jose解析jwt
        parseClaimsUseJOSE = parserJWTUseJOSE(jwtUseJOSE, new RSASSAVerifier((RSAPublicKey) publicKey));
        System.out.println(parseClaimsUseJOSE);
    }


    private String generateJWT(Key key) {
        Claims claims = Jwts.claims();
        claims.put("k1", "v1");
        claims.put("k2", "v2");
        // 生成jwt
        return Jwts.builder()
                // jwt签发者(预定义声明)
                .setIssuer("issuser")
                // jwt所面向的用户(预定义声明)
                .setSubject("test subject")
                // 接收jwt的一方(预定义声明)
                .setAudience("test aud")
                // jwt的唯一身份标识,主要用来作为一次性token,从而避免重放攻击(预定义声明)
                .setId(UUID.randomUUID().toString())
                // jwt签发时间(预定义声明)
                .setIssuedAt(new Date())
                // jwt过期时间(预定义声明)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                // 添加公共声明
                .addClaims(claims)
                // 设置密钥
                .signWith(key)
                .compact();
    }

    private String generateJWTUseJOSE(JWSSigner signer, JWSAlgorithm alg) throws Exception {
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(alg), new JWTClaimsSet.Builder()
                .issuer("issuser").subject("tes subject").audience("test aud")
                .jwtID(UUID.randomUUID().toString()).issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .claim("k1", "v1")
                .claim("k2", "v2")
                .build());
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    private Claims parserJWT(String jwt, Key key) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private JWTClaimsSet parserJWTUseJOSE(String jwt, JWSVerifier jwsVerifier) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(jwt);
        // 需要校验
        assertTrue(signedJWT.verify(jwsVerifier));
        return signedJWT.getJWTClaimsSet();
    }
}
