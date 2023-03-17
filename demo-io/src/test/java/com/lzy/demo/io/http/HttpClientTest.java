package com.lzy.demo.io.http;

import com.lzy.demo.io.bable.BladeApplication;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.cookie.BasicClientCookie;
import org.apache.hc.client5.http.impl.routing.DefaultRoutePlanner;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.http.protocol.BasicHttpContext;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.util.Timeout;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled("blade不支持java17,所以这边先排除掉,如果要运行的话,则这边需要降为java8,同时springboot需要使用2.7")
public class HttpClientTest {

    private CloseableHttpClient httpClient = HttpClients.createDefault();

    private static final Integer PORT = 19001;

    private static final String HOST = "http://127.0.0.1:" + PORT;

    private static BladeApplication bladeApplication = new BladeApplication(PORT);

    @BeforeAll
    public static void startApplication() throws InterruptedException {
        bladeApplication.start();
        // 等待blade启动
        Thread.sleep(500);
    }

    @AfterAll
    public static void stopApplication() {
        bladeApplication.stop();
    }

    /**
     * 测试get
     *
     * @throws Exception exception
     */
    @Test
    public void testGet() throws Exception {
        // HttpGet需要完整的url,所以这边使用URIBuilder构造uri
        URI uri = new URIBuilder(HOST).appendPath("rest/get/path").addParameter("queryParam", "queryParam value").build();
        // 也可以通过httpGet.setEntity()设置请求参数
        HttpGet httpGet = new HttpGet(uri);
        // 进行配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(2000)).setResponseTimeout(Timeout.ofMilliseconds(2000)).build();
        httpGet.setConfig(requestConfig);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            assertEquals(200, response.getCode());
            HttpEntity entity = response.getEntity();
            // EntityUtils.toString会关闭流
            String content = EntityUtils.toString(entity);
            System.out.println(content);
            assertThat(content).contains("queryParam value");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试post
     */
    @Test
    public void testPost() {
        HttpPost httpPost = new HttpPost(HOST + "/rest/post");
        // StringEntity如果没有指定ContentType, 也可以使用httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        StringEntity stringEntity = new StringEntity("{\"key\": \"value\"}", ContentType.APPLICATION_JSON);
        httpPost.setEntity(stringEntity);
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            System.out.println(content);
            assertThat(content).contains("value");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试put
     */
    @Test
    public void testPut() {
        HttpPut httpPut = new HttpPut(HOST + "/rest/put/1");
        httpPut.setEntity(new StringEntity("{\"key\": \"value\"}", ContentType.APPLICATION_JSON));
        try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            System.out.println(content);
            assertThat(content).contains("value");
            assertThat(content).contains("1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 测试delete
     */
    @Test
    public void testDelete() {
        HttpDelete httpDelete = new HttpDelete(HOST + "/rest/delete/1");
        try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            System.out.println(content);
            assertThat(content).contains("1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试form-data urlencoded, 一般的表单
     */
    @Test
    public void testFormUrlencoded() {
        HttpPost httpPost = new HttpPost(HOST + "/rest/form-data");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("formField", "form field value"));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            System.out.println(content);
            assertThat(content).contains("form field value");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试form-data, 带有文件上传的表单
     */
    @Test
    public void testFormData() {
        HttpPost httpPost = new HttpPost(HOST + "/rest/form-data");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        // 文件上传builder.addBinaryBody("upfile", file, ContentType.DEFAULT_BINARY, fileName);
        builder.addTextBody("formField", "form field value");
        httpPost.setEntity(builder.build());
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            System.out.println(content);
            assertThat(content).contains("form field value");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试header
     */
    @Test
    public void testHeader() {
        HttpGet httpGet = new HttpGet(HOST + "/rest/header");
        httpGet.addHeader("headerKey", "header value");
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            System.out.println(content);
            assertThat(content).contains("header value");
            assertEquals("headerKey override", response.getHeader("headerKey").getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试cookie
     *
     * @throws Exception exception
     */
    @Test
    public void testCookie() throws Exception {
        HttpGet httpGet = new HttpGet(HOST + "/rest/cookie");
        // 可以直接使用httpGet.addHeader(HttpHeaders.COOKIE, "cookieKey=cookie value")
        // 使用BasicCookieStore
        BasicCookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("cookieKey", "cookie value");
        // 获取设置domain
        cookie.setDomain(httpGet.getUri().getHost());
        cookieStore.addCookie(cookie);
        // 使用HttpContext为单个请求设置cookieStore
        HttpContext localContext = new BasicHttpContext();
        localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
        //也可以直接为整个httpClient设置cookieStore
        //httpClient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
        try (CloseableHttpResponse response = httpClient.execute(httpGet, localContext)) {
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            System.out.println(content);
            assertThat(content).contains("cookie value");
            // response.getHeaders(HttpHeaders.SET_COOKIE)获得设置的cookie
            assertThat(response.getHeaders(HttpHeaders.SET_COOKIE))
                    .anyMatch(header -> header.getValue().contains("add cookie value"));
            // 从cookieStore获取设置的cookie
            assertThat(cookieStore.getCookies())
                    .anyMatch(header -> header.getValue().contains("add cookie value"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * HttpClient代理
     * HttpClient底层跟URL发送请求一样,都是使用Socket的
     * 无proxy的时候,socket连接的是目标地址,然后发送请求
     * 有proxy的时候,socket连接的就是proxy,然后发送请求(使用源目标地址)
     * 发送的请求就是HTTP的报文格式
     * <p>
     * URL参考:sun.net.NetworkClient#doConnect
     * HttpClient参考: DefaultManagedHttpClientConnection.bind
     * 不同的地方在于, HttpURLConnection都有使用sun.net.spi.DefaultProxySelector进行选择proxy
     * 而HttpClient只有当使用了SystemDefaultRoutePlanner才会使用使用sun.net.spi.DefaultProxySelector进行选择proxy
     * <p>
     * 对于proxy,都是在DefaultRoutePlanner#determineRoute进行确定proxy
     * 区别在于是使用哪种的HttpRoutePlanner
     * 默认情况: 直接使用DefaultRoutePlanner,如果请求没有配置proxy,则不会使用proxy
     * HttpClients.createSystem(): 使用SystemDefaultRoutePlanner,会从sun.net.spi.DefaultProxySelector.select选择proxy
     * HttpClients.custom().setProxy(proxy).build(): 使用SystemDefaultRoutePlanner,直接使用设置的proxy
     *
     * @see DefaultRoutePlanner#determineRoute
     * @see sun.net.spi.DefaultProxySelector#select(URI)
     */
    @Test
    public void testProxy() {
        String proxyHost = "127.0.0.1";
        int proxyPort = 9090;

        // 默认情况下,http.proxyHost对它并不起作用,需要使用HttpClients.createSystem()创建HttpClient才能起作用
        System.setProperty("http.proxyHost", proxyHost);
        System.setProperty("http.proxyPort", Integer.toString(proxyPort));
        CloseableHttpClient systemClient = HttpClients.createSystem();

        // 使用system proxy的话, 会在sun.net.spi.DefaultProxySelector.select选择proxy
        // 默认情况下localhost|127.*|[::1]|0.0.0.0|[::0]都直接不使用代理,因此在这边设置了http.nonProxyHosts的值
        // vm参数使用-Dhttp.nonProxyHosts=这样就可以设置为空字符串
        System.setProperty("http.nonProxyHosts", "");
        HttpGet httpGet = new HttpGet(HOST + "/rest/get/system-proxy");

        try (CloseableHttpResponse response = systemClient.execute(httpGet)) {
            assertEquals(200, response.getCode());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 如果使用HttpClients.createDefault()的话,需要指定proxy
        HttpHost proxy = new HttpHost("http", proxyHost, proxyPort);
        // 直接为整个httpClient设置proxy: HttpClients.custom().setProxy(proxy).build();
        httpGet = new HttpGet(HOST + "/rest/get/proxy");
        // 为单个请求设置proxy
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        httpGet.setConfig(config);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            assertEquals(200, response.getCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
