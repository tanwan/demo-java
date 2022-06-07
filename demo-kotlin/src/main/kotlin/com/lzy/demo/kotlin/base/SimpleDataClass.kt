package com.lzy.demo.kotlin.base

/**
 * 数据类
 * 默认提供了equals()/hashCode(),toString(),componentN(),copy()
 * 主构造函数至少有一个参数
 *
 * @author lzy
 * @version v1.0
 */
data class SimpleDataClass(
        val intProperty: Int,
        val stringProperty: String?,
) {
    // 默认提供的equals()/hashCode(),toString(),componentN(),copy()都不会包含在class body声明的属性
    var bodyProperty: String? = null
}
