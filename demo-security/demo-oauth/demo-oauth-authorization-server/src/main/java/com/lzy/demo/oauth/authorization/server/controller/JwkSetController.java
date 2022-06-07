package com.lzy.demo.oauth.authorization.server.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * 方便测试,提供了private-key-jwt的公钥
 *
 * @author lzy
 * @version v1.0
 */
@Controller
public class JwkSetController {

    public static final KeyPair PRIVATE_KEY_JWT_KEY_PAIR;

    static {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyPairGenerator.initialize(2048);
        // 这边是直接生成了RSA的钥匙对,也可以从jks文件出获取(详见com.lzy.demo.jwt.JWKTest)
        PRIVATE_KEY_JWT_KEY_PAIR = keyPairGenerator.generateKeyPair();
    }


    /**
     * 获取的公钥
     *
     * @return the key
     */
    @GetMapping("/.demo-private-key-jwt/jwks.json")
    @ResponseBody
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) PRIVATE_KEY_JWT_KEY_PAIR.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}
