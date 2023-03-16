package com.lzy.demo.kotlin.base
// 继承open类,主构造函数的属性如果是父类的,则不需要用val/var声明
class SimpleInheritanceOpenClass(stringProperty: String, intProperty: Int, var ownerProperty: String) :
    SimpleOpenClass(stringProperty, intProperty) {

    // 可以继承open的方法
    override fun openFunc() {
        println("SimpleInheritanceOpenClass openFunc exec")
    }
}

// 继承抽象类和接口,使用主构造函数
open class SimpleInheritanceClass(stringProperty: String, intProperty: Int, var ownerProperty: String) :
    SimpleAbstractClass(stringProperty, intProperty), SimpleInterface {
    override fun funcInAbstract() {
        println("SimpleInheritanceClass funcInAbstract exec")
    }

    override fun funcInInterface() {
        println("SimpleInheritanceClass funcInInterface exec")
    }
}

// 声明的时候不带主构造函数
class SimpleInheritanceConstructorClass : SimpleInheritanceClass {
    constructor(stringProperty: String, intProperty: Int, ownerProperty: String) : super(
        stringProperty,
        intProperty,
        ownerProperty
    )

    constructor(stringProperty: String, intProperty: Int) : super(
        stringProperty,
        intProperty,
        "default ownerProperty value"
    )
}

// 实现接口的接口有以get开头的方法,同时该类也有这个字段, 有两种方法解决: 1.使用private, 2. 使用@JvmField
class OverrideGetterMethod(private val method1: String, @JvmField val method2: String) : SimpleInterface {
    override fun funcInInterface() {
        TODO("Not yet implemented")
    }

    // 使用private修饰, 同时重写方法
    override fun getMethod1(): String = method1

    // 使用@JvmField修饰, 同时重写方法
    override fun getMethod2(): String = method2
}