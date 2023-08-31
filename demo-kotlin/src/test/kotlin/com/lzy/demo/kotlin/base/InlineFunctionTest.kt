package com.lzy.demo.kotlin.base

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


/**
 *
 * @author lzy
 * @version v1.0
 */
class InlineFunctionTest {


    /**
     * 使用Idea -> Tools -> Kotlin -> Show Kotlin Bytecode查看字节码和反编译后的java代码
     *
     */
    @Test
    fun testInlineFunction() {
        val str = "string value"

        // 在Kotlin中每次声明一个Lambda表达式,就会在字节码中产生一个匿名类,该匿名类包含了一个invoke方法,作为Lambda的调用方法

        // 这边的lambda没有使用外部的变量,所以这边会直接使用匿名类的实例
        callLambda {
            println("callLambda")
        }
        // 这边的lambda使用了外部的变量,因此在调用的时候,会去new一个匿名类, 这样就会增加额外的开销
        // java的机制跟kotlin不一样,它使用了invokedynamic,所以没有这个问题
        callLambda {
            println("callLambda,str:$str")
        }

        // 使用内联函数会将调用的地方直接用内联函数的函数体和lambda替换
        callLambdaInLine {
            println("callLambdaInLine")
        }

        callLambdaInLine {
            println("callLambdaInLine,str:$str")
        }
    }

    /**
     * let内联函数
     */
    @Test
    fun testLet() {
        println("testLet"?.let {
            // 需要使用 it 来获取对象
            // 可以在对象需要判空,又需要做很多处理的时候使用
            println(it)
            println(it)
            // 最后一行是返回值
            it + " override"
        })

        val simpleClass = SimpleClass("string value", 3).let {
            // 在这里可以调用对象的任意方法和属性
            it.defaultNullProperty = "defaultNullProperty override"
            // apply是返回当前对象
            it
        }
        assertEquals("defaultNullProperty override", simpleClass.defaultNullProperty)
    }

    /**
     * apply内联函数
     */
    @Test
    fun testApply() {
        val simpleClass = SimpleClass("string value", 3).apply {
            // 在这里可以调用对象的任意方法和属性
            defaultNullProperty = "defaultNullProperty override"
            // apply是返回当前对象
        }

        assertEquals("defaultNullProperty override", simpleClass.defaultNullProperty)
    }

    /**
     * run内联函数
     * let和apply的结合体
     */
    @Test
    fun testRun() {
        println(SimpleClass("string value", 3).run {
            // 在这里可以调用对象的任意方法和属性(let需要使用it)
            println(stringProperty)
            // 最后一行是返回值
            stringProperty + " override"
        })
    }


    fun callLambda(block: () -> Unit) {
        println("call fun callLambda")
        block()
    }

    /**
     * 内联函数
     */
    inline fun callLambdaInLine(block: () -> Unit) {
        println("call fun callLambdaInLine")
        block()
    }
}