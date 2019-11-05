/*
 * Created by LZY on 2017/7/9 15:26.
 */
package com.lzy.demo.oauth.authorization.server.custom;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * 在存储AccessToken之前(DefaultTokenServices#createAccessToken)可以对AccessToken进行增强,jwt就是利用这个来实现的
 *
 * @author LZY
 * @version v1.0
 */
public class CustomTokenEnhancer implements TokenEnhancer {
    /**
     * 对access_token进行增强
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>(1);
        // 返回的access_token多了ownName这个字段
        additionalInfo.put("ownName", authentication.getName());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
