package com.lzy.demo.groovy.base

import org.junit.jupiter.api.Test

class ClassTest {


    /**
     * 类和对象
     */
    @Test
    void testClass() {
        // 有构造函数
        // 使用new
        def instance = new WithConstructorClass("string", 23)
        println("new instance:$instance")
        // 使用list as
        instance = ['string', 23] as WithConstructorClass
        println("list as instance:$instance")
        // 使用list赋值
        WithConstructorClass instanceUseList = ['string', 23]
        println("list class:$instanceUseList")

        // 无构造函数
        // 使用key:value传参
        instance = new NoConstructorClass(stringProperty: 'string', intProperty: 23)
        println("use map instance:$instance")
        // 可以只给部分属性赋值
        instance = new NoConstructorClass(stringProperty: 'string')
        println("use map only one variable instance:$instance")
    }

    /**
     * 使用with
     */
    @Test
    void testWith() {
        def instance = new NoConstructorClass()
        instance.with {
            // 使用with,将闭包的delegate指向instance
            stringProperty = 'string'
            intProperty = 23
        }
        println("instance with:$instance")
    }
}
