package com.lzy.demo.io.resource;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.AntPathMatcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNull;

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
        assertNull(getClass().getClassLoader().getResource("notExistPath"));
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


    /**
     * 获取多个资源,使用通配符,ClassLoader#getResources并不支持通配符
     * 在文件系统里,其实是使用File列出所有文件,再加上AntPathMatcher进行匹配
     *
     * @throws Exception exception
     * @see PathMatchingResourcePatternResolver#doFindPathMatchingFileResources(org.springframework.core.io.Resource, java.lang.String)
     */
    @Test
    public void testResourcesWithWildcard() throws Exception {
        //获取classpath路径下的com/lzy/**/*.class
        String pathPattern = "com/lzy/**/*.class";

        Set<String> result = new HashSet<>();
        //先获取出没有通配符的根目录,这边为了方便,直接使用了com/lzy
        Enumeration<URL> urls = getClass().getClassLoader().getResources("com/lzy");
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String fullPathPattern = getClass().getClassLoader().getResource("").getPath() + pathPattern;
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            getResourcesWithWildcard(new File(url.toURI()), result, antPathMatcher, fullPathPattern);
        }
        result.forEach(System.out::println);
    }

    private void getResourcesWithWildcard(File rootFile, Set<String> result, AntPathMatcher pathMatcher, String fullPathPattern) {
        for (File file : rootFile.listFiles()) {
            if (file.isDirectory()) {
                getResourcesWithWildcard(file, result, pathMatcher, fullPathPattern);
            } else {
                if (pathMatcher.match(fullPathPattern, file.getPath())) {
                    result.add(file.getName());
                }
            }
        }
    }
}
