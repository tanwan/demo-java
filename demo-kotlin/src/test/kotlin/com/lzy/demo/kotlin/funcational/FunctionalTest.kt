package com.lzy.demo.kotlin.funcational

import org.junit.jupiter.api.Assertions.assertEquals
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
        assertEquals("simpleFun override", ret)

        // lambda表达式
        val funLambda = SimpleFunInterface { "simpleFun $it" }
        ret = funLambda.simpleFun("lambda")
        assertEquals("simpleFun lambda", ret)
    }
}