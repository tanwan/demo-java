package com.lzy.demo.kotlin.base

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class BaseTest {

    /**
     * 变量
     */
    @Test
    fun testVariables() {
        // val: 常量
        val a: Int = 1
        // 自动推断类型
        val b = 2
        assert(b is Int)
        // 常量,先声明再赋值
        val c: Int
        c = 3
        println("a:$a,b:$b,c:$c")
        // var: 变量
        var d = 4
        println("d:$d")
        d = 5
        println("d:$d")

        // 使用？表示变量可以为null
        val nullableVal: Int? = null
        println("nullableVal:$nullableVal")
        var nullableVar: Int? = 3
        nullableVar = null
        println("nullableVar:$nullableVar")
    }

    /**
     * 函数
     */
    @Test
    fun testFunc() {
        // 函数调用
        func()

        // 带参数的函数
        var ret = funcWithParameter("str value", 3)
        println("funcWithParameter:$ret")

        // 指定参数名的调用
        ret = funcWithParameter(i = 3, str = "str = str value")
        println("funcWithParameter named argument,$ret")

        // 函数体是表达式的函数
        println("funcExpression:${funcExpression(3, 23)}")

        // 有默认参数的函数
        funcWithDefaultParameter()

        // 挂起函数
        runBlocking {
            // 这边会挂起当前协程
            simpleSuspend("simpleSuspend")
        }
    }

    /**
     * when,相当于switch
     */
    @Test
    fun testWhen() {
        for (x in 1..7) {
            // 只会进入一个分支
            when (x) {
                1 -> println("x:$x,x == 1")
                // 可以多个条件
                2, 3 -> println("x:$x,x == 2 or x==3")
                // 可以使用range
                in 4..5 -> println("x:$x,x is in the range")
                // 可以使用!in
                !in 7..8 -> println("x:$x,x is not in the range")
                // else 可以省略
                else -> {
                    println("x:$x,x is not in case")
                }
            }
        }

        for (e in SimpleEnum.values()) {
            when (e) {
                SimpleEnum.Enum1 -> println("enum1")
                SimpleEnum.Enum2 -> println("enum2")
            }
        }
    }

    /**
     * equals和判断同实例
     */
    @Test
    fun testEqualsAndSame() {
        val a = SimpleDataClass(3, "string value")
        val b = SimpleDataClass(3, "string value")
        // ==相当于java的equals
        Assertions.assertTrue(a == b)
        Assertions.assertFalse(a != b)
        Assertions.assertEquals(a, b)
        // ===相当于java的==
        Assertions.assertTrue(a !== b)
        Assertions.assertFalse(a === b)
        Assertions.assertNotSame(a, b)
    }

    /**
     * fun: 声明函数, Unit表示没有返回值,可以省略
     */
    fun func(): Unit {
        println("call func")
    }

    /**
     * 参数: var:Type
     */
    fun funcWithParameter(str: String, i: Int): String {
        return "str:$str,i:$i"
    }

    /**
     * 参数有默认值
     */
    fun funcWithDefaultParameter(str: String = "default string") {
        println("funcWithDefaultParameter str:$str")
    }

    /**
     * 函数也可以是表达式
     */
    fun funcExpression(a: Int, b: Int) = a + b


    /**
     * 可以挂起当前协程的函数,只能在协程中调用
     */
    suspend fun simpleSuspend(name: String): String {
        delay(1000L)
        return "$name success"
    }


}