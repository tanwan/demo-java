package com.lzy.demo.kotlin.collections

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 *
 * @author lzy
 * @version v1.0
 */
class MapTest {

    /**
     * map声明
     */
    @Test
    fun testDeclare() {
        // 使用k to value创建map
        val map = mutableMapOf("1" to 1, "2" to 2)
        println(map)

        // 空map
        val emptyMap = mutableMapOf<String, Int>()
        assertTrue(emptyMap.isEmpty())
        emptyMap["3"] = 3
        println(emptyMap)

        // 不可变的空map(无法插入数据)
        val immutableEmptyMap = emptyMap<String, Int>()
        assertTrue(immutableEmptyMap.isEmpty())
    }


    /**
     * map基础操作
     */
    @Test
    fun testBase() {
        val map = mutableMapOf("k1" to "v1", "k2" to "v2")

        // 增加
        // 使用put和map[k]都可以赋值
        map.put("k3", "v3")
        map["k5"] = "v5"
        println(map)

        // 修改
        map["k5"] = "v5 override"
        assertEquals("v5 override", map["k5"])

        // 查询
        // 使用get或者[k]都可以取值
        assertEquals("v1", map.get("k1"))
        assertEquals("v1", map["k1"])
        assertNull(map["notExistKey"])
        assertNull(map.get("notExistKey"))
    }

    /**
     * 遍历
     */
    @Test
    fun testIterate() {
        val map = mutableMapOf("k1" to "v1", "k2" to "v2", "k3" to "v3")

        // 使用for entry
        for (entry in map) {
            println("for entry, key:${entry.key},value:${entry.value}")
        }

        // 使用forEach entry
        map.forEach { entry -> println("forEach entry, key:${entry.key},value:${entry.value}") }

        // 使用forEach k,v
        map.forEach { (k, v) -> println("forEach k,v, key:$k,value:$v") }
    }
}