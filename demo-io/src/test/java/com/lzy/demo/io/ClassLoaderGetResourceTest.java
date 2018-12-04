/*
 * Created by lzy on 2018/11/6 10:39 PM.
 */
package com.lzy.demo.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

/**
 * class获取资源Test
 *
 * @author lzy
 * @version v1.0
 */
public class ClassLoaderGetResourceTest {

    /**
     * 获取一个存在的资源
     */
    @Test
    public void testGetExistResource() {
        System.out.println(getClass().getClassLoader().getResource("com/lzy/demo/io/ClassLoaderGetResourceTest.class"));
    }

    /**
     * 获取一个不存在的资源
     */
    @Test
    public void testGetNotExistResource() {
        //返回null
        Assertions.assertNull(getClass().getClassLoader().getResource("notExistPath"));
    }


    /**
     * 获取一个Jar包里的资源
     */
    @Test
    public void testGetJARResource() {
        System.out.println(getClass().getClassLoader().getResource("META-INF/MANIFEST.MF"));
    }


    /**
     * 获取多个资源
     *
     * @throws IOException the io exception
     */
    @Test
    public void testGetJARResources() throws IOException {
        String classPath = System.getProperty("java.class.path");
        Arrays.stream(classPath.split(":")).forEach(System.out::println);
        System.out.println("----------------------------------");
        Enumeration<URL> urls = getClass().getClassLoader().getResources("META-INF/MANIFEST.MF");
        Collections.list(urls).forEach(System.out::println);
    }

    @Test
    public void test(){
        ProtectionDomain protectionDomain = getClass().getProtectionDomain();
        CodeSource codeSource = protectionDomain.getCodeSource();
        System.out.println(codeSource);
    }


}
