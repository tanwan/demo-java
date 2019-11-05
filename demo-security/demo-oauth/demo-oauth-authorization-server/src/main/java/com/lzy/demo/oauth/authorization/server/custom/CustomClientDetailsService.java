/*
 * Created by lzy on 2019/10/24 5:58 PM.
 */
package com.lzy.demo.oauth.authorization.server.custom;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 自定义的ClientDetailsService
 *
 * @author lzy
 * @version v1.0
 * @see org.springframework.security.oauth2.provider.client.JdbcClientDetailsService
 */
@Component
public class CustomClientDetailsService implements ClientDetailsService {

    @Resource
    private ClientDetailsHolder clientDetailsHolder;

    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    /**
     * spring oauth这边有坑,会调用这个方法4次
     *
     * @see org.springframework.security.oauth2.provider.client.JdbcClientDetailsService#loadClientByClientId(String)
     */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        if (clientDetailsHolder.getClientDetails() == null) {
            if ("code".equals(clientId) || "client".equals(clientId) || "password".equals(clientId) || "implicit".equals(clientId)) {
                //这边为了方便,所以没有使用数据库
                //如果没指定redirect,那么将跳转到参数给定的地址
                //如果有指定redirect,那么参数给定的地址要跟指定的redirect一致
                BaseClientDetails clientDetails = new BaseClientDetails(clientId, null, "read,write", "authorization_code,implicit,password,client_credentials,refresh_token", "CLIENT", "http://www.baidu.com,http://127.0.0.1:28080/authorization-code");
                clientDetails.addAdditionalInformation("info", "lzy");
                // 因为使用DelegatingPasswordEncoder,因此密码需要有{scrypt}来表示该密码是使用何种方式加密的
                String password = passwordEncoder.encode("secret");
                clientDetails.setClientSecret(password);
                // 过期时间
                clientDetails.setAccessTokenValiditySeconds(7200);
                clientDetailsHolder.setClientDetails(clientDetails);
            } else if ("resourceServer".equals(clientId)) {
                // 给资源服务器来校验token使用
                BaseClientDetails clientDetails = new BaseClientDetails("resourceServer", null, "", "", "");
                String password = passwordEncoder.encode("secret");
                clientDetails.setClientSecret(password);
                clientDetailsHolder.setClientDetails(clientDetails);
                clientDetailsHolder.setClientDetails(clientDetails);
            }
        }
        return clientDetailsHolder.getClientDetails();
    }
}
