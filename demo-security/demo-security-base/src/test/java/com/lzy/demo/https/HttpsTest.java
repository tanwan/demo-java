package com.lzy.demo.https;

import io.vertx.core.Vertx;
import io.vertx.core.http.ClientAuth;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.JksOptions;
import io.vertx.core.net.PemTrustOptions;
import io.vertx.core.net.impl.TrustAllTrustManager;
import io.vertx.ext.web.Router;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jsse.provider.BouncyCastleJsseProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class HttpsTest {


    private HttpServer httpsServer;

    /**
     * 单向https
     *
     * @throws Exception Exception
     */
    @Test
    public void testHttps() throws Exception {
        SSLContext sslContext = SSLContext.getInstance("tlsv1.3");
        // 单向认证时keyManagers为null
        sslContext.init(null, new TrustAllTrustManager[]{TrustAllTrustManager.INSTANCE}, new SecureRandom());

        URL url = new URL("https://127.0.0.1:8443");
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());

        // sun.net.www.protocol.https.HttpsClient#checkURLSpoofing会使用HostnameChecker来校验hostName
        // 通过SAN/SubjectAltName(Subject Alternative Names)来校验
        // 如果HostnameChecker校验不过的话,则使用HostnameVerifier进行校验
        urlConnection.setHostnameVerifier((host, cert) -> {
            // 这边可以自定义校验HostName
            System.out.println(host);
            System.out.println(cert);
            return true;
        });

        // \\A为正则\A, 表示字符串的开头(^表示一行中的开头), Scanner使用\A就可以读取整个输入流
        Scanner scanner = new Scanner(urlConnection.getInputStream()).useDelimiter("\\A");
        System.out.println(scanner.next());
    }

    /**
     * https双向验证
     *
     * @throws Exception Exception
     */
    @Test
    public void testTwoWayAuthentication() throws Exception {
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");

        // Key: 客户端用来加密的私钥, https双向认证使用, 可以使用javax.net.ssl.keyStore指定默认的KeyStore
        // keyStore的jks可以有多个私钥
        // 服务端在Server Hello的过程中会将它信任的所有客户端证书的certificate authorities(CN, OU, O, L, ST, C)发送给客户端
        // 客户端根据certificate authorities的信息在KeyStore选择对应的私钥进行加密
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(getClass().getResourceAsStream("/demo-java.jks"), "123456".toCharArray());
        keyManagerFactory.init(keyStore, "123456".toCharArray());
        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

        // Trust: 客户端用来验证服务端的证书, 可以使用javax.net.ssl.trustStore指定默认的TrustStore
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        KeyStore serverKeyStore = KeyStore.getInstance("JKS");
        // https-server-cert.jks是从https-server.cer转换的,只包含服务端的证书
        serverKeyStore.load(getClass().getResourceAsStream("/https-server-cert.jks"), "123456".toCharArray());
        trustManagerFactory.init(serverKeyStore);
        // TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        // 不使用TrustManagerFactory创建TrustManager, 也可以直接重写TrustManager
        TrustManager[] trustManagers = customTrustManager();

        sslContext.init(keyManagers, trustManagers, new SecureRandom());

        URL url = new URL("https://127.0.0.1:8443");
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());

        // \\A为正则\A, 表示字符串的开头(^表示一行中的开头), Scanner使用\A就可以读取整个输入流
        Scanner scanner = new Scanner(urlConnection.getInputStream()).useDelimiter("\\A");
        System.out.println(scanner.next());
    }


    /**
     * bctls有自己的一套tls流程
     *
     * @throws Exception exception
     * @see sun.security.ssl.SSLSocketImpl SSLSocketImpl:jdk进行https握手
     * @see sun.security.ssl.X509TrustManagerImpl X509TrustManagerImpl: jdk进行证书校验的类
     * @see org.bouncycastle.jsse.provider.ProvSSLSocketDirect ProvSSLSocketDirect:bctls进行https握手
     * @see org.bouncycastle.jsse.provider.ProvX509TrustManager ProvX509TrustManager: bctls进行证书校验
     */
    @Test
    public void testBouncyCastleHttps() throws Exception {
        URL url = new URL("https://baidu.com");
        // 核心都在于SSLSocketFactory, HttpsURLConnection存在SSLSocketFactory实例, 有默认值(默认值也可以指定), 也可以通过HttpsURLConnection指定
        // SSLSocketFactory用来负责创建socket, bctls使用ProvSSLSocketFactory, jdk使用SSLSocketFactoryImpl
        // ProvSSLSocketFactory也就创建出了ProvSSLSocketDirect
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        Scanner scanner = new Scanner(urlConnection.getInputStream()).useDelimiter("\\A");
        System.out.println(scanner.next());
    }

    private TrustManager[] customTrustManager() throws Exception {
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) certFactory
                .generateCertificate(getClass().getResourceAsStream(("/https-server.cer")));

        return new TrustManager[]{new X509TrustManager() {
            /**
             * 提供CA的证书列表
             * @return X509Certificate[]
             * @see sun.security.ssl.AbstractTrustManagerWrapper#checkServerTrusted(X509Certificate[], String, Socket)
             */
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                // 空或空数组表示信任所有的CA证书
                // 在checkServerTrusted方法之后, 会使用这边的CA证书对服务端的证书再进行验证
                return null;
            }

            /**
             * 此方法只对服务端有用, 服务端用来验证客户端的证书
             * vert.x的服务端使用以下方法进行验证客户端的证书
             * @see sun.security.ssl.X509TrustManagerImpl#checkClientTrusted(X509Certificate[], String, javax.net.ssl.SSLEngine)
             *
             * @param certs 证书
             * @param authType authType
             */
            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            /**
             * 用来验证服务端的证书
             *
             * @param certs 证书链, 依次是服务器证书  中间证书  根证书
             * @param authType authType
             * @see sun.security.ssl.AbstractTrustManagerWrapper#checkServerTrusted(X509Certificate[], String, Socket)
             * @exception CertificateException
             */
            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                try {
                    certs[0].checkValidity(new Date());
                    certs[0].verify(cert.getPublicKey());
                } catch (GeneralSecurityException e) {
                    throw new CertificateException();
                }
            }
        }
        };
    }


    @BeforeEach
    public void beforeEach(TestInfo testInfo) throws InterruptedException {
        String methodName = testInfo.getTestMethod().map(Method::getName).orElse("");

        if (methodName.contains("BouncyCastle")) {
            //将BouncyCastleProvider和BouncyCastleJsseProvider放在前两位
            Security.insertProviderAt(new BouncyCastleProvider(), 1);
            Security.insertProviderAt(new BouncyCastleJsseProvider(), 2);
            Arrays.stream(Security.getProviders()).forEach(System.out::println);
        }
        startHttpsServer(methodName.contains("TwoWay"));

    }


    private void startHttpsServer(boolean clientAuth) throws InterruptedException {
        // Key: 存放包括私钥和公钥的信息
        // Trust: 只存放受信任的公钥信息
        Vertx vertx = Vertx.vertx();
        HttpServerOptions httpServerOptions = new HttpServerOptions()
                .setSsl(true)
                // setKeyStoreOptions: 使用jks
                // setPemKeyCertOptions: 使用pem, 可以分别设置证书和私钥
                .setKeyCertOptions(new JksOptions()
                        // https-server.jks带有Subject Alternative Names
                        .setPath("https-server.jks")
                        .setPassword("123456")
                );
        if (clientAuth) {
            httpServerOptions
                    // https双向验证, 开启验证客户端的证书
                    //@see sun.security.ssl.X509TrustManagerImpl#checkClientTrusted(X509Certificate[], String, javax.net.ssl.SSLEngine)
                    .setClientAuth(ClientAuth.REQUIRED)
                    // 服务端还需要配置客户端证书
                    // 这边可以直接使用JksOptions直接配置客户端的jks, 这边的jks可以存在多个公钥, 服务端会把所有的公钥证书的certificate authorities发给客户端
                    // .setTrustOptions(new JksOptions()
                    // .setPath("demo-java.jks")
                    // .setPassword("123456"))
                    // 只有客户端证书的话, 也可以使用PemTrustOptions直接配置客户端的公钥证书
                    .setTrustOptions(new PemTrustOptions()
                            .addCertPath("demo-java-cert.pem")
                    );
        }

        httpsServer = vertx.createHttpServer(httpServerOptions);
        Router router = Router.router(vertx);
        router.route().handler(ctx -> ctx.response().end("Hello, HTTPS World!"));
        httpsServer.requestHandler(router).listen(8443);
        Thread.sleep(1000L);
    }

    @AfterEach
    public void afterEach() {
        httpsServer.close();
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.removeProvider(BouncyCastleJsseProvider.PROVIDER_NAME);
    }
}
