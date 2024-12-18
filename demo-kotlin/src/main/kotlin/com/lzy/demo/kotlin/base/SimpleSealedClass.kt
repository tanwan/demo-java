package com.lzy.demo.kotlin.base

/**
 * sealed class是抽象类
 * 同时又具有枚举的功能, 主要用于when的判断
 * 子类必须放在同模块同包下, 建议在同一个文件下(sealed class文件不大的前提下)
 */
sealed class SimpleSealedClass(val commonProperty: String) {
    abstract val customProperty: String

    /**
     * 同data class一样,且是单例
     */
    data object DataObject : SimpleSealedClass("DataObject") {
        override val customProperty = "DataObject"
    }

    class SealedClass(
        val ownProperty: String,
        override val customProperty: String
    ) : SimpleSealedClass("SealedClass")

    data class DataClassClass(
        val ownProperty: String,
        override val customProperty: String
    ) : SimpleSealedClass("DataClassClass")
}