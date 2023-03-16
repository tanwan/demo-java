package com.lzy.demo.kotlin.collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 *
 * @author lzy
 * @version v1.0
 */
class ListTest {


    /**
     * 声明
     */
    @Test
    fun testDeclare() {
        // listOf创建list无法修改元素
        val immutableList = listOf(1, 2, 3)
        // immutableList没有add/remove方法
        // immutableList.add()
        assertEquals(3, immutableList.size)


        // mutableListOf创建的list可以修改
        val mutableList = mutableListOf(1)
        mutableList.add(2)
        mutableList.add(3)
        assertEquals(3, mutableList.size)
    }


    /**
     * 遍历
     */
    @Test
    fun testIterate() {
        val list = listOf("a", "b", "c")
        // 使用 for in
        for (i in list) {
            println("for i:$i")
        }
        // 使用forEach
        list.forEach {
            println("forEach:$it")
        }

        // 使用 withIndex
        for ((index, v) in list.withIndex()) {
            println("withIndex:index:$index,v:$v")
        }
        // 使用forEachIndexed
        list.forEachIndexed { index, v -> println("forEachIndexed:index:$index,v:$v") }

        // 使用indices
        for (index in list.indices) {
            println("indices:index:$index,v:${list[index]}")
        }
    }

    /**
     * 操作
     */
    @Test
    fun testOperation() {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8)
        // find: 返回第一个符合的元素
        assertEquals(6, list.find { it > 5 })
        // filter: 过滤,返回list
        assertEquals(listOf(6, 7, 8), list.filter { it > 5 })

        // reduce: 第一个参数是当前值,第二个参数是元素值
        assertEquals(36, list.reduce { sum, i -> sum + i })

        // map
        assertEquals(listOf(2, 4, 6, 8, 10, 12, 14, 16), list.map { it * 2 })

        // 组合
        assertEquals(42, list.map { it * 2 }.filter { it > 10 }.reduce { sum, i -> sum + i })
    }
}