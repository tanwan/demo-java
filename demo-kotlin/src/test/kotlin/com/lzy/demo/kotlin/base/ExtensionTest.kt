package com.lzy.demo.kotlin.base

import org.junit.jupiter.api.Test


/**
 *
 * @author lzy
 * @version v1.0
 */
class ExtensionTest {

    /**
     * 扩展类
     */
    @Test
    fun testExtension() {
        val simpleClass = SimpleClass("extension")

        // 使用类名加函数名可以扩展类
        fun SimpleClass.extensionFunInMethod(param: String) {
            // 这边的this就是SimpleClass的实例
            println("extensionFunInMethod, param:$param,this.stringProperty:${this.stringProperty}")
        }
        simpleClass.extensionFunInMethod("extensionFunInMethod")
        simpleClass.extensionFunClass("extensionFunClass")
        simpleClass.extensionFunInOtherFile("extensionFunInOtherFile")
        simpleClass.extensionFunInFile("extensionFunInFile")
        // 在其它类中扩展的只能在那个类使用
        //simpleClass.extensionFunInOtherClass("extensionFunInOtherClass")
        SimpleExtension().callExtensionFun(simpleClass)
    }

    fun SimpleClass.extensionFunClass(param: String) {
        // 这边的this就是SimpleClass的实例
        println("extensionFunClass, param:$param,this.stringProperty:${this.stringProperty}")
    }
}

// 扩展方法可以定义在其它文件中
fun SimpleClass.extensionFunInFile(param: String) {
    // 这边的this就是SimpleClass的实例
    println("extensionFunInFile, param:$param,this.stringProperty:${this.stringProperty}")
}