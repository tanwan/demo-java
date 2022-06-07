package com.lzy.demo.kotlin.base

/**
 * 使用object声明的类就是一个单例
 * 构造函数都是无参的
 * object也可以继承其它类和接口
 *
 * @author lzy
 * @version v1.0
 */
object SimpleObject {

    // 声明的属性只能在init中初始化
    val property: String

    init {
        property = "property value"
    }

    fun func() {
        println("func exec,property:$property")
    }
}