package com.lzy.demo.kotlin.base

import kotlin.reflect.KProperty


/**
 *
 * @author lzy
 * @version v1.0
 */
class SimpleDelegateProperty {

    var value: String = ""

    // operator用来重载运算符
    operator fun getValue(thisRef: Nothing?, property: KProperty<*>): String {
        println("getValue: thisRef:$thisRef,property:$property")

        return this.value
    }


    operator fun setValue(thisRef: Nothing?, property: KProperty<*>, value: String) {
        println("setValue: thisRef:$thisRef,property:$property, value:$value")
        this.value = value
    }
}