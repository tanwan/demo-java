package com.lzy.demo.spring.boot.loader;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.loader.LaunchedURLClassLoader;
import org.springframework.boot.loader.jar.JarFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * spring boot loader测试
 * 测试流程: 执行gradle的buildSpringBootLoaderTestJar task生成jar包
 * 为什么使用spring-boot-configuration-processor?
 * 因为spring-boot-configuration-processor不在运行此测试类的classpath路径下
 * 这样获取该依赖下的类就可以排除是该测试类依赖进来的
 *
 * @author lzy
 * @version v1.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpringBootLoaderTest {
    private String className = "org.springframework.boot.configurationprocessor.TypeUtils";


    private URL[] getUrls() {
        String springbootVersion = LaunchedURLClassLoader.class.getPackage().getImplementationVersion();

        try {
            //URL类是依据URL Protocol Handler来处理URL字符串的,内置提供http、https、ftp、file和jar协议的URL Protocol Handler
            //可以使用java.protocol.handler.pkgs属性添加自己的Handler
            //比如JarFile.registerUrlProtocolHandler();就是添加了org.springframework.boot.loader.jar.Handler来支持嵌套jar
            return new URL[]{new URL(String.format("jar:file:%s/build/libs/demo-spring-1.0.jar!/BOOT-INF/lib/spring-boot-configuration-processor-%s.jar!/",
                    System.getProperty("user.dir"), springbootVersion))};
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 没有注册org.springframework.boot.loader.jar.Handler的情况下,无法加载嵌套jar
     * 这个test要先执行,否则后面的JarFile.registerUrlProtocolHandler()会影响这个test
     */
    @Test
    @Order(1)
    public void testNoRegisterUrlProtocolHandlerLoadClassFromNestedJar() {
        //使用的是sun.net.www.protocol.jar.Handler,嵌套jar以!/分隔
        //这边需要分两种情况,一种是url以!/结尾,一种不以!/结尾
        //1. 如果不以!/结尾,那么URLClassPath获取Loader的时候会使用JarLoader(会在URL前再添加jar:,但是这个无法解析嵌套jar）
        //2. 以!/结尾的,URLClassPath获取Loader会使用Loader(可以查找嵌套jar的时候,但是!/会作为名称的一部分,因此无法找到,不加!/虽然能查找到,但是返回的还是外层的jar)
        //当有嵌套jar的时候,它返回的还是外层的jar,因此内部jar的class无法被加载
        LaunchedURLClassLoader launchedURLClassLoader = new LaunchedURLClassLoader(getUrls(), getClass().getClassLoader());
        URLClassLoader urlClassLoader = new URLClassLoader(getUrls());
        assertThatExceptionOfType(ClassNotFoundException.class).isThrownBy(() ->
                launchedURLClassLoader.loadClass(className));
        assertThatExceptionOfType(ClassNotFoundException.class).isThrownBy(() ->
                urlClassLoader.loadClass(className));
    }

    /**
     * 注册org.springframework.boot.loader.jar.Handler的情况下,使用LaunchedURLClassLoader可以加载嵌套jar
     */
    @Test
    public void testSpringBootLoaderLoadClassFromNestedJar() {
        //org.springframework.boot.loader.jar.Handler处理嵌套jar的流程是这样的:
        //1. 把url根据分隔符!/进行拆分
        //2. 把外部jar读取成org.springframework.boot.loader.jar.JarFile(继承java.util.jar.JarFile),同时会把jar内部项读到JarFileEntries中(文件名使用hash值,按升序排列)
        //3. 读取内部项的时候,先获取内部jar的名称的hash值,然后根据hash从JarFileEntries获取org.springframework.boot.loader.jar.JarFile(首先把整个jar读取成字节数组,然后根据各个内部项的偏移量,可以读取指定项的数据)
        //4. 把org.springframework.boot.loader.jar.JarFile封装成org.springframework.boot.loader.jar.JarURLConnection,然后URL打开连接的时候,就可以获取到嵌套的jar了
        JarFile.registerUrlProtocolHandler();
        LaunchedURLClassLoader classLoader = new LaunchedURLClassLoader(getUrls(), getClass().getClassLoader());
        assertThatCode(() -> {
            Class clazz = classLoader.loadClass(className);
            //使用LaunchedURLClassLoader,在defineClass的时候会先definePackage,使用JarFile获取出Manifest,然后进行definePackage
            System.out.println(clazz.getPackage().getImplementationVersion());
        }).doesNotThrowAnyException();
    }

    /**
     * 注册org.springframework.boot.loader.jar.Handler的情况下,使用URLClassLoader可以加载嵌套jar
     */
    @Test
    public void testURLClassLoaderLoadClassFromNestedJar() {
        //注册org.springframework.boot.loader.jar.Handler协议处理器
        JarFile.registerUrlProtocolHandler();
        URLClassLoader classLoader = new URLClassLoader(getUrls());
        assertThatCode(() -> {
            Class clazz = classLoader.loadClass(className);
            //使用URLClassLoader,使用的是URLClassPath来读取jar,然后封装成Resource(获取不到Manifest),因此definePackage的时候,无法获得这些信息
            System.out.println(clazz.getPackage().getImplementationVersion());
        }).doesNotThrowAnyException();
    }


    /**
     * 从嵌套jar加载资源
     */
    @Test
    public void testGetResourceFromNestedJar() {
        //注册org.springframework.boot.loader.jar.Handler协议处理器
        JarFile.registerUrlProtocolHandler();
        //会从传入LaunchedURLClassLoader的urls查找到资源
        LaunchedURLClassLoader classLoader = new LaunchedURLClassLoader(getUrls(), getClass().getClassLoader());
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
        JarFile.registerUrlProtocolHandler();
        JarFile jarFile = new JarFile(new File(String.format("%s/build/libs/demo-spring-1.0.jar", System.getProperty("user.dir"))));
        Enumeration<JarEntry> enumeration = jarFile.entries();
        while (enumeration.hasMoreElements()) {
            System.out.println(enumeration.nextElement().getName());
        }
    }
}
