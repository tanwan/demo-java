package com.lzy.demo.groovy.metadata

import org.junit.jupiter.api.Test

class ExtensionTest {

    /**
     * 扩展方法
     */
    @Test
    void testExtension() {

        // 扩展String的实例方法
        println("extend".extensionFunc("variable value"))

        // 扩展String的静态方法
        println(String.extensionStaticFunc("variable value"))
    }
}
