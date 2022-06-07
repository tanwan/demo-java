package com.lzy.demo.kotlin.base

open class SimpleOpenClass(val stringProperty: String, val intProperty: Int) {


    /**
     * 默认为final方法
     */
    fun finalFunc() {
        println("SimpleOpenClass finalFunc exec")
    }

    /**
     * 带open的为非final方法
     */
    open fun openFunc() {
        println("SimpleOpenClass openFunc exec")
    }
}