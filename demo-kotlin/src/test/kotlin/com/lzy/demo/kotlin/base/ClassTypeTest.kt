package com.lzy.demo.kotlin.base

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ClassTypeTest {

    /**
     * data class
     */
    @Test
    fun testDataClass() {
        val dataClass = SimpleDataClass(3, "string value", "private")
        dataClass.bodyProperty = "string value"

        // 默认提供了toString,不过在class body声明的属性没有在toString中
        assertEquals(
            "SimpleDataClass(intProperty=3, stringProperty=string value, privateProperty=private)",
            dataClass.toString()
        )
        assertEquals("string value", dataClass.bodyProperty)

        // copy
        var copy = dataClass.copy()
        assertEquals(
            "SimpleDataClass(intProperty=3, stringProperty=string value, privateProperty=private)",
            copy.toString()
        )
        // 不会复制在class body声明的属性
        assertNull(copy.bodyProperty)
        // copy可以传入参数覆盖原有的值
        copy = dataClass.copy(intProperty = 1)
        assertEquals(
            "SimpleDataClass(intProperty=1, stringProperty=string value, privateProperty=private)",
            copy.toString()
        )
    }


    /**
     * object,也就是单例
     */
    @Test
    fun testObject() {
        // 不用创建实例就可以直接调用函数
        SimpleObject.func()
    }

    /**
     * sealed class
     */
    @Test
    fun testSealedClass() {
        val list = listOf(
            SimpleSealedClass.DataObject,
            SimpleSealedClass.SealedClass("own SealedClass", "custom SealedClass"),
            SimpleSealedClass.DataClassClass("own DataClassClass", "custom DataClassClass")
        )
        for (sealed in list) {
            // 用于when的判断
            when (sealed) {
                SimpleSealedClass.DataObject -> {
                    assertEquals("DataObject", sealed.customProperty)
                }

                is SimpleSealedClass.SealedClass -> {
                    assertEquals("own SealedClass", sealed.ownProperty)
                    assertEquals("custom SealedClass", sealed.customProperty)
                }

                is SimpleSealedClass.DataClassClass -> {
                    assertEquals("own DataClassClass", sealed.ownProperty)
                    assertEquals("custom DataClassClass", sealed.customProperty)
                }
            }
        }
    }
}