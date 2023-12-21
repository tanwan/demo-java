package com.lzy.demo.spring.boot.loader;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.loader.launch.LaunchedClassLoader;
import org.springframework.boot.loader.net.protocol.Handlers;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * spring boot loader测试
 * 测试流程: 执行gradle的buildSpringBootLoaderTestJar task生成jar包
 * 为什么使用spring-boot-configuration-processor?
 * 因为spring-boot-configuration-processor不在运行此测试类的classpath路径下
 * 这样获取该依赖下的类就可以排除是该测试类依赖进来的
 *
 * @author lzy
 * @version v1.0
 * @see <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/executable-jar.html">executable-jar</a>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpringBootLoaderTest {
    private String className = "org.springframework.boot.configurationprocessor.TypeUtils";


    private URL[] getUrls() {
        String springbootVersion = LaunchedClassLoader.class.getPackage().getImplementationVersion();

        try {
            //URL类是依据URL Protocol Handler来处理URL字符串的,内置提供http、https、ftp、file和jar协议的URL Protocol Handler
            //通过java.protocol.handler.pkgs可以添加自己的Handler, 详见org.springframework.boot.loader.net.protocol.Handlers.register()
            URL url = URI.create(String.format("jar:nested:%s/build/libs/demo-spring-1.0.jar/!BOOT-INF/lib/spring-boot-configuration-processor-%s.jar!/",
                    System.getProperty("user.dir"), springbootVersion)).toURL();
            System.out.println(url);
            return new URL[]{url};
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 没有注册org.springframework.boot.loader.jar.Handler的情况下,无法加载嵌套jar
     * 这个test要先执行,否则后面的Handlers.register()会影响这个test
     *
     * @throws Exception e
     */
    @Test
    @Order(1)
    public void testNoRegisterUrlProtocolHandlerLoadClassFromNestedJar() throws Exception {
        String springbootVersion = LaunchedClassLoader.class.getPackage().getImplementationVersion();
        URL url = URI.create(String.format("jar:file:%s/build/libs/demo-spring-1.0.jar!/BOOT-INF/lib/spring-boot-configuration-processor-%s.jar!/",
                System.getProperty("user.dir"), springbootVersion)).toURL();
        //使用的是sun.net.www.protocol.jar.Handler,嵌套jar以!/分隔
        //在jdk.internal.loader.URLClassPath.getLoader(java.net.URL)这边使用Loader加载jar
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
        assertThatExceptionOfType(ClassNotFoundException.class).isThrownBy(() ->
                urlClassLoader.loadClass(className));
    }

    /**
     * 注册org.springframework.boot.loader.net.protocol.jar.Handler的情况下,使用LaunchedURLClassLoader可以加载嵌套jar
     */
    @Test
    public void testSpringBootLoaderLoadClassFromNestedJar() {
        Handlers.register();
        LaunchedClassLoader classLoader = new LaunchedClassLoader(false, getUrls(), getClass().getClassLoader());
        assertThatCode(() -> {
            Class clazz = classLoader.loadClass(className);
            //使用LaunchedURLClassLoader,在defineClass的时候会先definePackage,使用JarFile获取出Manifest,然后进行definePackage
            assertNotNull(clazz.getPackage().getImplementationVersion());
            System.out.println(clazz.getPackage().getImplementationVersion());
        }).doesNotThrowAnyException();
    }


    /**
     * 注册org.springframework.boot.loader.jar.Handler的情况下,使用URLClassLoader可以加载嵌套jar
     */
    @Test
    public void testURLClassLoaderLoadClassFromNestedJar() {
        //注册org.springframework.boot.loader.net.protocol.jar.Handler协议处理器
        Handlers.register();
        URLClassLoader classLoader = new URLClassLoader(getUrls());
        assertThatCode(() -> {
            Class clazz = classLoader.loadClass(className);
            //使用URLClassLoader,使用的是URLClassPath来读取jar,然后封装成Resource(获取不到Manifest),因此definePackage的时候,无法获得这些信息
            assertNull(clazz.getPackage().getImplementationVersion());
        }).doesNotThrowAnyException();
    }


    /**
     * 从嵌套jar加载资源
     */
    @Test
    public void testGetResourceFromNestedJar() {
        //注册org.springframework.boot.loader.net.protocol.jar.Handler协议处理器
        Handlers.register();
        //会从传入LaunchedURLClassLoader的urls查找到资源
        LaunchedClassLoader classLoader = new LaunchedClassLoader(false, getUrls(), getClass().getClassLoader());
        URL url = classLoader.getResource(className.replace(".", "/") + ".class");
        System.out.println(url);
    }


    /**
     * 测试JarFile
     *
     * @throws IOException IOException
     */
    @Test
    public void testJarFile() throws IOException {
        Handlers.register();
        URL url = URI.create(String.format("jar:file:%s/build/libs/demo-spring-1.0.jar!/", System.getProperty("user.dir"))).toURL();
        url.openConnection().getContent();
        JarFile jarFile = (JarFile) url.openConnection().getContent();
        Enumeration<JarEntry> enumeration = jarFile.entries();
        while (enumeration.hasMoreElements()) {
            System.out.println(enumeration.nextElement().getName());
        }
    }

}
