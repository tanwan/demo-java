package com.lzy.demo.kotlin.base


/**
 *
 * @author lzy
 * @version v1.0
 */
class SimpleExtension {

    // 扩展方法可以定义在类中
    fun SimpleClass.extensionFunInOtherClass(param: String) {
        // 这边的this就是SimpleClass的实例
        println("extensionFunInClass, param:$param,this.stringProperty:${this.stringProperty}")
    }

    fun callExtensionFun(simpleClass: SimpleClass) {
        simpleClass.extensionFunInOtherClass("extensionFunInOtherClass")
    }
}


// 扩展方法可以定义在其它文件中
fun SimpleClass.extensionFunInOtherFile(param: String) {
    // 这边的this就是SimpleClass的实例
    println("extensionFunInOtherFile, param:$param,this.stringProperty:${this.stringProperty}")
}