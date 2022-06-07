package com.lzy.demo.kotlin.funcational


/**
 * 函数式接口, 只有一个抽象方法
 *
 * @author lzy
 * @version v1.0
 */
fun interface SimpleFunInterface {

    /**
     * 抽象方法
     */
    fun simpleFun(str: String): String


    /**
     * 默认函数
     */
    fun defaultFunc(str: String) {
        println("defaultFunc exec,str:$str")
    }
}