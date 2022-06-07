package com.lzy.demo.groovy.base

import org.junit.jupiter.api.Test

import java.util.function.Consumer


/**
 * 函数式编程
 *
 * @author lzy
 * @version v1.0
 */
class FunctionalTest {

    /**
     * 方法引用
     */
    @Test
    void testMethodReference() {
        // 方法引用,使用java的::
        execFunction(System.out::println)
        // groovy可以使用.&
        execFunction(System.out.&println)
    }


    /**
     * 函数式
     */
    @Test
    void testFunctional() {
        def list = [1, 2, 3, 4, 5, 6, 7, 8]
        // split: 将list按给定的闭包分为两个list
        def splitList = list.split { it > 4 }
        println("splitList:$splitList")

        // find,findAll: 相当于filter,find返回第一个符合的元素,findAll返回所有
        assert list.find { it > 5 } == 6
        assert list.findAll { it > 5 } == [6, 7, 8]

        // inject: 相当于reduce,闭包的第一个参数是当前值,第二个参数是元素值
        assert list.inject { sum, i -> sum + i } == 36

        // collect:相当于map
        assert list.collect { it * 2 } == [2, 4, 6, 8, 10, 12, 14, 16]

        // flatten:相当于flattenMap
        assert [[1, 2], [3, 4]].flatten { it * 2 } == [2, 4, 6, 8]

        // 组合
        assert list.collect { it * 2 }.findAll { it > 10 }.inject { sum, i -> sum + i } == 42
    }


    def execFunction(Consumer consumer) {
        consumer.accept("execFunction")
    }
}
