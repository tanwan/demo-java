package com.lzy.demo.kotlin.base


/**
 * 接口
 *
 * @author lzy
 * @version v1.0
 */
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
}