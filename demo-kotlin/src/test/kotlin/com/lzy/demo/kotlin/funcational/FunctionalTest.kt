package com.lzy.demo.kotlin.funcational

import org.junit.jupiter.api.Test


/**
 *
 * @author lzy
 * @version v1.0
 */
class FunctionalTest {

    /**
     * 函数式接口
     */
    @Test
    fun testFunInterface() {
        val funInterface = object : SimpleFunInterface {
            override fun simpleFun(str: String): String {
                return "simpleFun $str"
            }
        }
        var ret = funInterface.simpleFun("override")
        println("ret:$ret")

        // lambda表达式
        val funLambda = SimpleFunInterface { "simpleFun $it" }
        ret = funLambda.simpleFun("lambda")
        println("ret:$ret")
    }


    /**
     * 函数式
     */
    @Test
    fun testFunctional() {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8)

        // find: 返回第一个符合的元素
        assert(list.find { it > 5 } == 6)
        // filter: 过滤,返回list
        assert(list.filter { it > 5 } == listOf(6, 7, 8))

        // reduce: 第一个参数是当前值,第二个参数是元素值
        assert(list.reduce { sum, i -> sum + i } == 36)

        // map
        assert(list.map { it * 2 } == listOf(2, 4, 6, 8, 10, 12, 14, 16))

        // 组合
        assert(list.map { it * 2 }.filter { it > 10 }.reduce { sum, i -> sum + i } == 42)
    }
}