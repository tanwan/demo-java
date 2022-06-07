package com.lzy.demo.kotlin.collections

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
        // listOf创建list无法修改
        val immutableListOf = listOf(1, 2, 3)
        assert(immutableListOf.size == 3)

        // mutableListOf创建的list可以修改
        val mutableList = mutableListOf(1)
        mutableList.add(2)
        mutableList.add(3)
        assert(mutableList.size == 3)
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

}