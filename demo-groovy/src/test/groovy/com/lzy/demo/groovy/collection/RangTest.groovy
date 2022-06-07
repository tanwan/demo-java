package com.lzy.demo.groovy.collection

import org.junit.jupiter.api.Test

class RangTest {

    @Test
    void testDeclare() {
        // 闭区间,包括最后一个元素
        def range = 3..5
        assert range.size() == 3
        assert range.get(2) == 5
        assert range[2] == 5
        assert range.getClass() == IntRange
        // IntRange是List的子类
        assert range instanceof List
        assert range.contains(3)
        assert range.from == 3
        assert range.to == 5

        // 开区间,不包括最后一个元素
        range = 3..<5
        assert range.size() == 2
        assert range.to == 4
    }

    /**
     * 遍历
     */
    @Test
    void testIterate() {
        (3..5).each { println(it) }
    }
}
