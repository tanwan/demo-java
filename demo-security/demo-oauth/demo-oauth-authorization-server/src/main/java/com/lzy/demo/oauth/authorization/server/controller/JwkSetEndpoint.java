/*
 * Created by lzy on 2019/11/1 9:44 PM.
 */
package com.lzy.demo.oauth.authorization.server.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * The type Jwk set endpoint.
 *
 * @author lzy
 * @version v1.0
 */
@FrameworkEndpoint
public class JwkSetEndpoint {

    private KeyPair keyPair;

    /**
     * Instantiates a new Jwk set endpoint.
     *
     * @param keyPair the key pair
     */
    public JwkSetEndpoint(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    /**
     * 获取的nimbusds-jose-jwt公钥
     *
     * @param principal the principal
     * @return the key
     */
    @GetMapping("/.demo/jwks.json")
    @ResponseBody
    public Map<String, Object> getKey(Principal principal) {
        RSAPublicKey publicKey = (RSAPublicKey) this.keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
}
