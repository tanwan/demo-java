package com.lzy.demo.kotlin.base


/**
 * 抽象类
 *
 * @author lzy
 * @version v1.0
 */
abstract class SimpleAbstractClass(val stringProperty: String, val intProperty: Int) {

    /**
     * 抽象方法
     */
    abstract fun funcInAbstract()
}