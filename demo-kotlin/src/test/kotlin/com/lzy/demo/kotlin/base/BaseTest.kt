package com.lzy.demo.kotlin.base

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
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
        assertTrue(b is Int)
        // 常量,先声明再赋值
        val c: Int
        c = 3
        assertEquals(1, a)
        assertEquals(2, b)
        assertEquals(3, c)
        // var: 变量
        var d = 4
        assertEquals(4, d)
        d = 5
        assertEquals(5, d)

        // 使用？表示变量可以为null
        val nullableVal: Int? = null
        assertNull(nullableVal)
        var nullableVar: Int? = 3
        nullableVar = null
        assertNull(nullableVal)
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
        assertEquals("str:str value,i:3", ret)

        // 指定参数名的调用
        ret = funcWithParameter(i = 3, str = "str = str value")
        assertEquals("str:str = str value,i:3", ret)

        // 函数体是表达式的函数
        assertEquals(26, funcExpression(3, 23))

        // 有默认参数的函数
        funcWithDefaultParameter()

        // 挂起函数
        runBlocking {
            // 这边会挂起当前协程
            simpleSuspend("simpleSuspend")
        }
    }

    @Test
    fun testFuncWithLambda() {
        // 参数为lambda的情况, 直接使用代码块
        funcAcceptLambda { println("funcAcceptLambda") }
        // lambda有默认值, 所以这边不需要传参
        funcAcceptLambdaWithDefault()

        // lambda有类型限制
        val instance = SimpleClass(1)
        funcAcceptLambdaWithClass(instance) {
            // 这边的this就是SimpleClass
            defaultNullProperty = "funcAcceptLambdaWithClass"
        }
        assertEquals("funcAcceptLambdaWithClass", instance.defaultNullProperty)
    }

    /**
     * when,相当于switch
     */
    @Test
    fun testWhen() {
        for (x in 1..7) {
            // 只会进入一个分支
            when (x) {
                1 -> assertEquals(1, x)
                // 可以多个条件
                2, 3 -> assertThat(x).isIn(2, 3)
                // 可以使用range
                in 4..5 -> assertThat(x).isBetween(4, 5)
                // 可以使用!in
                !in 7..8 -> {
                    assertTrue(x < 7 || x > 8)
                }
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
        val a = SimpleDataClass(3, "string value", "private")
        val b = SimpleDataClass(3, "string value", "private")
        // ==相当于java的equals
        assertTrue(a == b)
        assertFalse(a != b)
        assertEquals(a, b)
        // ===相当于java的==
        assertTrue(a !== b)
        assertFalse(a === b)
        assertNotSame(a, b)
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
     * 参数为lambda
     */
    fun funcAcceptLambda(block: () -> Unit) {
        block()
    }

    /**
     * 参数为lambda, 默认值为{}
     */
    fun funcAcceptLambdaWithDefault(block: () -> Unit = {}) {
        block()
    }

    /**
     * 参数为lambda,并且lambda有类型限制
     * SimpleClass.() -> Unit
     * 1. SimpleClass.: 可以理解成此时的lambda是一个delegate为SimpleClass的闭包,因此需要通过SimpleClass的实例进行调用
     * lambda也可以访问SimpleClass的变量和方法, 相当于this是SimpleClass的实例
     * 2. (): lambda无参数
     * 3. Unit:无返回值
     */
    fun funcAcceptLambdaWithClass(instance: SimpleClass, block: SimpleClass.() -> Unit) {
        // 如果这个方法是声明在SimpleClass,那边就可以直接使用block(), 因为没有声明在SimpleClass类中, 所以这边需要使用instance.block()
        instance.block()
    }


    /**
     * 可以挂起当前协程的函数,只能在协程中调用
     */
    suspend fun simpleSuspend(name: String): String {
        delay(1000L)
        return "$name success"
    }
}