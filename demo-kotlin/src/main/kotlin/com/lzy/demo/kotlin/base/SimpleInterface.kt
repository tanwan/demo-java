package com.lzy.demo.kotlin.base

interface SimpleInterface {

    /**
     * 接口方法
     */
    fun funcInInterface()

    /**
     * 默认函数
     */
    fun defaultFunc(str: String) {
        println("defaultFunc exec,str:$str")
    }

    /**
     * 以get开头的方法
     */
    fun getMethod1(): String = "SimpleInterface#getMethod1"

    /**
     * 以get开头的方法
     */
    fun getMethod2(): String = "SimpleInterface#getMethod2"
}