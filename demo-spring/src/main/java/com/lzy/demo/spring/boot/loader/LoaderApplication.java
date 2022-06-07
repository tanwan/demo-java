package com.lzy.demo.spring.boot.loader;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

public class LoaderApplication {


    /**
     * 测试资源路径
     * 1.idea直接运行
     * 2.使用gradle的插件application打包
     * 3.使用spring-boot的插件打包
     *
     * @param args the input arguments
     * @throws Exception the io exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println("----------------class path------------------");
        String classPath = System.getProperty("java.class.path");
        Arrays.stream(classPath.split(":")).forEach(System.out::println);
        System.out.println("----------------getResource------------------");
        URL url = LoaderApplication.class.getClassLoader().getResource("java/lang/String.class");
        System.out.println(url);
        System.out.println("----------------getResources------------------");
        //获取资源的顺序是按classpath的顺序,spring-boot打的包的classpath只有自己本身,它获取的资源是嵌套jar,顺序是嵌套jar的包名hash值,排序
        //见org.springframework.boot.loader.jar.JarFileEntries
        Enumeration<URL> urls = LoaderApplication.class.getClassLoader().getResources("META-INF/MANIFEST.MF");
        Collections.list(urls).forEach(System.out::println);
    }
}
