package com.lzy.demo.groovy.base

import org.junit.jupiter.api.Test

class FunctionTest {

    /**
     * 调用有Map入参的函数
     */
    @Test
    void testCallFuncWithMap() {
        // 函数的入参是map,则可以使用k:v传参
        funcWithMap(var1: 'var1', var2: 'var2')
        // 入参的map后面还有参数,可以在map后面继续传参
        funcWithMap(var1: 'var1', var2: 'var2', 3)
        funcWithMap(3, var1: 'var1', var2: 'var2')
        // 如果入参的map前面有参数,则map需要使用[]传参
        funcWithMap(3, [var1: 'var1', var2: 'var2'])
    }

    /**
     * 方法带有默认值
     */
    @Test
    void testFuncWithDefaultVariable() {
        funcWithDefaultVariable("use default variable")
        funcWithDefaultVariable("override variable", 3)
    }

    def funcWithMap(Map map) {
        println("funcWithMap: map:$map")
    }

    def funcWithMap(Map map, Integer afterMap) {
        println("funcWithMap:afterMap:$afterMap, map:$map")
    }

    def funcWithMap(Integer beforeMap, Map map) {
        println("funcWithMap:beforeMap:$beforeMap, map:$map")
    }

    def funcWithDefaultVariable(String name, Integer defaultVariable = 23) {
        println("funcWithDefaultVariable,name:$name,defaultVariable:$defaultVariable")
    }
}
