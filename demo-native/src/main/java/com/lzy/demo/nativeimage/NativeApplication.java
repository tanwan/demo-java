package com.lzy.demo.nativeimage;

public class NativeApplication {

    /**
     * 使用graalvm的插件用来打包native image, 需要使用graal的jdk
     * ./gradlew :demo-native:nativeCompile
     *
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println("hello world");
    }
}
