package com.lzy.demo.kotlin.collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class ArrayList {

    /**
     * 声明
     */
    @Test
    fun testDeclare() {
        val array = arrayOf(1, 2, 3)
        assertEquals(3, array.size)
    }

    /**
     * 跟List的互转
     */
    @Test
    fun testConvert() {
        var array = arrayOf(1, 2, 3)
        var list = array.toList()
        assertEquals(3, array.size)

        list = listOf(1, 2, 3)
        array = list.toTypedArray()
        assertEquals(3, array.size)
    }
}