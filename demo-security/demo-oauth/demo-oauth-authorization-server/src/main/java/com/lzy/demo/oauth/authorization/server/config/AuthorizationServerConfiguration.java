package com.lzy.demo.oauth.authorization.server.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.ClientSecretAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.JwtClientAssertionAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationValidator;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientCredentialsAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2RefreshTokenAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2AccessTokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.NimbusJwkSetEndpointFilter;
import org.springframework.security.oauth2.server.authorization.web.OAuth2AuthorizationEndpointFilter;
import org.springframework.security.oauth2.server.authorization.web.OAuth2AuthorizationServerMetadataEndpointFilter;
import org.springframework.security.oauth2.server.authorization.web.OAuth2TokenEndpointFilter;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

/**
 * 授权服务器
 *
 * @author LZY
 * @version v1.0
 * @see <a href="https://github.com/spring-projects/spring-authorization-server">spring-authorization-server</a>
 */
@Configuration
public class AuthorizationServerConfiguration {


    /**
     * 对授权服务器进行配置
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        // 对授权服务器进行一些默认的配置
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        // 支持OpenID Connect 1.0, scope如果有openid的话,需要配置这个
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());
        return http.formLogin(Customizer.withDefaults()).build();
    }

    /**
     * RegisteredClientRepository client的repository
     * 对client进行认证的时候,会使用到RegisteredClientRepository
     *
     * @param jdbcTemplate the jdbc template
     * @return the registered client repository
     * @see H2DBConfig
     * @see ClientSecretAuthenticationProvider#authenticate(Authentication) client认证
     * @see JwtClientAssertionAuthenticationProvider#authenticate(Authentication) CLIENT_SECRET_JWT和PRIVATE_KEY_JWT的认证
     * @see JwtClientAssertionAuthenticationProvider.JwtClientAssertionDecoderFactory#createDecoder(RegisteredClient) JWT解析
     * @see OAuth2AuthorizationCodeRequestAuthenticationValidator
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        // 使用DelegatingPasswordEncoder,默认为BCryptPasswordEncoder,如果要使用其它的,则可以定义DelegatingPasswordEncoder
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // 存入DB时Set类型使用逗号分隔,对象类型使用JSON序列化,See RegisteredClientParametersMapper
        // 具体的数据可以在h2 console查看
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("demo-client").clientName("demo-client-name")
                // 因为使用DelegatingPasswordEncoder,因此密码需要有{scrypt}来表示该密码是使用何种方式加密的
                .clientSecret(passwordEncoder.encode("123456"))
                // 该client的认证方法
                // 使用Basic Auth
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                // 放在请求参数中(client_id,client_secret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                // 该client支持的获取Access Token的方式
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                // 该client支持的重定向地址
                .redirectUri("http://127.0.0.1:18080/redirect.html")
                .redirectUri("http://127.0.0.1:28080/login/oauth2/code/demo-oauth-code")
                // 该client支持的scope
                // openid会自动授权
                .scope(OidcScopes.OPENID).scope("message:read").scope("message:write")
                // 设置token的一些配置,format,过期时间
                // SELF_CONTAINED: 带有签名和时间限制,可以在在本地直接验证,比如JWT
                // REFERENCE(也被称为opaque): 将token存储在一些数据存储中,验证需要跟存储的数据做比较
                .tokenSettings(TokenSettings.builder().accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED).accessTokenTimeToLive(Duration.ofHours(1)).build())
                // 设置一些客户端配置,可以配置jwkSetUrl
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        // 使用CLIENT_SECRET_JWT的client
        RegisteredClient clientSecretJwt = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("demo-client-secret-jwt").clientName("demo-client-secret-jwt-name")
                // HS256算法要求密码要大于256位,
                .clientSecret("12345678901234567890123456789012")
                // 不需要传递client_secret,请求参数添加:client_assertion_type=urn:ietf:params:oauth:client-assertion-type:jwt-bearer&client_assertion=JWT
                // JWT是使用clientSecret作为SecretKey生成的,算法在clientSettings指定
                // JWT需要iss(clientId),sub(clientId),aud(授权服务器的iss(ProviderSettings配置的)),exp
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_JWT)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope(OidcScopes.OPENID).scope("message:read")
                // CLIENT_SECRET_JWT使用MacAlgorithm的算法
                .clientSettings(ClientSettings.builder().tokenEndpointAuthenticationSigningAlgorithm(MacAlgorithm.HS256).build()).build();
        // 使用PRIVATE_KEY_JWT的client
        RegisteredClient privateKeyJwt = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("demo-private-key-jwt").clientName("demo-private-key-jwt-name")
                // 跟CLIENT_SECRET_JWT不一样的地方在于client_assertion使用JWT生成的方法不一样
                // 该client不再需要client_secret, 它使用私钥生成JWT,然后需要向授权服务器提供公钥,也可以通过配置jwkSetUrl
                .clientAuthenticationMethod(ClientAuthenticationMethod.PRIVATE_KEY_JWT)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope(OidcScopes.OPENID).scope("message:read")
                // PRIVATE_KEY_JWT使用SignatureAlgorithm的算法
                .clientSettings(ClientSettings.builder().tokenEndpointAuthenticationSigningAlgorithm(SignatureAlgorithm.RS256)
                        // PRIVATE_KEY_JWT的client不需要配置secret,校验JWT的话,会去调用jwkSetUrl,所以需要配置jwkSet
                        // 这边为了测试方便,在本应用直接提供了此client的公钥
                        .jwkSetUrl("http://127.0.0.1:18080/.demo-private-key-jwt/jwks.json")
                        .build()).build();

        RegisteredClientRepository registeredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
        registeredClientRepository.save(registeredClient);
        registeredClientRepository.save(clientSecretJwt);
        registeredClientRepository.save(privateKeyJwt);
        return registeredClientRepository;
    }

    /**
     * 用来保存token
     *
     * @param jdbcTemplate               the jdbc template
     * @param registeredClientRepository the registered client repository
     * @return the o auth 2 authorization service
     * @see OAuth2TokenEndpointFilter 生成token的Filter
     * @see OAuth2AuthorizationEndpointFilter 生成code的Filter
     * @see OAuth2ClientCredentialsAuthenticationProvider#authenticate(Authentication) client_credentials
     * @see OAuth2AuthorizationCodeRequestAuthenticationProvider.OAuth2AuthorizationCodeGenerator code模式, 生成code
     * @see OAuth2AuthorizationCodeRequestAuthenticationProvider#authenticateAuthorizationRequest code模式, 保存code
     * @see OAuth2AuthorizationCodeAuthenticationProvider#authenticate(Authentication) authorization_code,生成token
     * @see OAuth2RefreshTokenAuthenticationProvider#authenticate refresh_token模式,生成token
     * @see DelegatingOAuth2TokenGenerator#generate 生成token
     * @see JwtGenerator#generate(OAuth2TokenContext)  生成token(SELF_CONTAINED)
     * @see OAuth2AccessTokenGenerator#generate 生成token(REFERENCE)
     */
    @Bean
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        // token保存到DB
        return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
    }

    /**
     * 授权码模式需要进行授权
     * 如果用户没有授权,则需要用户进行授权
     * 如果用户已经授权过,则可以直接返回code
     *
     * @param jdbcTemplate               the jdbc template
     * @param registeredClientRepository the registered client repository
     * @return the o auth 2 authorization consent service
     * @see OAuth2AuthorizationCodeRequestAuthenticationProvider#authenticateAuthorizationRequest(Authentication)
     * @see OAuth2AuthorizationCodeRequestAuthenticationProvider#authenticateAuthorizationConsent(Authentication)
     */
    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }


    /**
     * SELF_CONTAINED格式的token需要JWKSource,存在JWKSource才能创建出JwtGenerator(生成jwt的token)
     *
     * @return the jwk source
     * @throws Exception the exception
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        // 这边是直接生成了RSA的钥匙对,也可以从jks文件出获取(详见com.lzy.demo.jwt.JWKTest)
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        JWK jwk = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();

        // JWK也有多种,比如RSAKey,ECKey,OctetKeyPair,这边的JWKSet可以包含多种JKW,然后生成token的时候选择匹配的JWK
        JWKSet jwkSet = new JWKSet(jwk);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * 配置一些端点的路径,比如获取token的路径,获取code的路径,issuer(资源服务器会使用)
     *
     * @return the provider settings
     * @see OAuth2TokenEndpointFilter 生成token的Filter
     * @see OAuth2AuthorizationEndpointFilter 生成code的Filter
     * @see NimbusJwkSetEndpointFilter 获取公钥
     * @see OAuth2AuthorizationServerMetadataEndpointFilter 元数据
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

}
