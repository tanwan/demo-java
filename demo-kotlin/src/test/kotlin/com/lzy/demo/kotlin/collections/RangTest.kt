package com.lzy.demo.kotlin.collections

import org.junit.jupiter.api.Test

/**
 *
 * @author lzy
 * @version v1.0
 */
class RangTest {

    /**
     * 遍历
     */
    @Test
    fun testRange() {
        // 闭区间,包括最后一个元素
        for (i in 1..3) {
            println("for i:$i")
        }
        // 开区间,不包括最后一个元素
        for (i in 1 until 3) {
            println("for until i:$i")
        }

        for (i in 3 downTo 1) {
            println("for downTo i:$i")
        }

        for (i in 1..6 step 2) {
            println("for step i:$i")
        }
    }
}