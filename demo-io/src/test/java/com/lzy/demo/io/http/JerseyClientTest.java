package com.lzy.demo.io.http;

import com.lzy.demo.io.bable.BladeApplication;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class JerseyClientTest {

    private static final Integer PORT = 19002;

    private static final String HOST = "http://127.0.0.1:" + PORT;

    private static BladeApplication bladeApplication = new BladeApplication(PORT);

    private Client client = ClientBuilder.newClient();

    private WebTarget rootWebTarget = client.target(HOST);

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
     */
    @Test
    public void testGet() {
        WebTarget webTarget = rootWebTarget.path("rest/get/path")
                .queryParam("queryParam", "queryParam value");
        Map<String, Object> response = webTarget.request()
                // 这边使用了GenericType,也可以使用Class
                .accept(MediaType.APPLICATION_JSON).get(new GenericType<Map<String, Object>>() {
                });
        System.out.println(response);
        assertThat(response.get("queryParam")).isEqualTo("queryParam value");
    }

    /**
     * 测试post
     */
    @Test
    public void testPost() {
        WebTarget webTarget = rootWebTarget.path("rest/post");
        Map<String, Object> response = webTarget.request()
                .post(Entity.entity(Collections.singletonMap("key", "value"), MediaType.APPLICATION_JSON), new GenericType<Map<String, Object>>() {
                });
        System.out.println(response);
        assertThat(response.toString()).contains("value");
    }


    /**
     * 测试put
     */
    @Test
    public void testPut() {
        WebTarget webTarget = rootWebTarget.path("/rest/put/1");
        Map<String, Object> response = webTarget.request().put(Entity.entity(Collections.singletonMap("key", "value"), MediaType.APPLICATION_JSON), new GenericType<Map<String, Object>>() {
        });
        System.out.println(response);
        assertThat(response.toString()).contains("value");
    }


    /**
     * 测试delete
     */
    @Test
    public void testDelete() {
        WebTarget webTarget = rootWebTarget.path("/rest/delete/1");
        Map<String, Object> response = webTarget.request().accept(MediaType.APPLICATION_JSON).delete(new GenericType<Map<String, Object>>() {
        });
        System.out.println(response);
        assertThat(response.get("id")).isEqualTo(1);
    }

    /**
     * 测试form-data urlencoded, 一般的表单
     */
    @Test
    public void testFormUrlencoded() {
        WebTarget webTarget = rootWebTarget.path("/rest/form-data");
        Form form = new Form();
        form.param("formField", "form field value");
        Map<String, Object> response = webTarget.request()
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED), new GenericType<Map<String, Object>>() {
                });
        System.out.println(response);
        assertThat(response.toString()).contains("form field value");
    }

    /**
     * 测试form-data, 带有文件上传的表单
     */
    @Test
    public void testFormData() {
        // 需要注册MultiPartFeature
        WebTarget webTarget = ClientBuilder.newBuilder().register(MultiPartFeature.class).build().target(HOST).path("/rest/form-data");
        FormDataMultiPart multipart = new FormDataMultiPart().field("formField", "form field value");
        Map<String, Object> response = webTarget.request()
                .post(Entity.entity(multipart, multipart.getMediaType()), new GenericType<Map<String, Object>>() {
                });
        System.out.println(response);
        assertThat(response.toString()).contains("form field value");
    }

    /**
     * 测试header
     */
    @Test
    public void testHeader() {
        WebTarget webTarget = rootWebTarget.path("/rest/header");
        Response response = webTarget.request().header("headerKey", "header value").get();
        Map<String, Object> body = response.readEntity(new GenericType<Map<String, Object>>() {
        });
        System.out.println(body);
        assertThat(body.get("headerKey")).isEqualTo("header value");
        assertThat(response.getHeaderString("headerKey")).isEqualTo("headerKey override");
    }

    /**
     * 测试cookie
     */
    @Test
    public void testCookie() {
        WebTarget webTarget = rootWebTarget.path("/rest/cookie");
        Response response = webTarget.request().cookie("cookieKey", "cookie value").get();
        Map<String, Object> body = response.readEntity(new GenericType<Map<String, Object>>() {
        });
        System.out.println(body);
        assertThat(body.get("cookieKey")).isEqualTo("cookie value");
        assertThat(response.getCookies().get("addCookie").getValue()).isEqualTo("add cookie value");
    }


    /**
     * 代理
     * 底层使用URL#openConnection
     * 所以只需要配置http.proxyHost/proxyPort即可使用proxy
     * <p>
     * 使用@Disabled避免http.proxyHost影响到其它测试(使用idea执行整个类的测试)
     *
     * @see org.glassfish.jersey.client.HttpUrlConnectorProvider.DefaultConnectionFactory#getConnection
     */
    @Test
    @Disabled
    public void testProxy() {
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "9090");
        // 同样使用sun.net.spi.DefaultProxySelector.select选择proxy
        System.setProperty("http.nonProxyHosts", "");
        WebTarget webTarget = rootWebTarget.path("rest/get/proxy");
        Response response = webTarget.request().get();
        assertThat(response.getStatus()).isEqualTo(200);
    }
}
