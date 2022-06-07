package com.lzy.demo.kotlin.base


/**
 *
 * @author lzy
 * @version v1.0
 */
class SimpleCompanion {
    /**
     * kotlin没有static关键字,可以使用伴生对象提供静态成员,类名可以省略
     * 一个类只能有一个伴生对象
     */
    companion object CompanionObject {
        val property = "CompanionObject value"
        fun companionFunc() {
            println("CompanionObject companionFunc exec")
        }
    }
}