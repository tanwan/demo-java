/*
 * Created by lzy on 2019/10/26 1:49 PM.
 */
package com.lzy.demo.oauth.authorization.server.custom;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * 保存ClientDetails,由于spring oauth会调用UserDetailsService#loadUserByUsername 4次,因此使用这个来存ClientDetails
 *
 * @author lzy
 * @version v1.0
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ClientDetailsHolder {

    private ClientDetails clientDetails;

    public ClientDetails getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }
}
