package com.lzy.demo.kotlin.base

// 继承open类,主构造函数的属性如果是父类的,则不需要用val/var声明
class SimpleInheritanceOpenClass(stringProperty: String, intProperty: Int, var ownerProperty: String) : SimpleOpenClass(stringProperty, intProperty) {

    // 可以继承open的方法
    override fun openFunc() {
        println("SimpleInheritanceOpenClass openFunc exec")
    }
}

// 继承抽象类和接口,使用主构造函数
open class SimpleInheritanceClass(stringProperty: String, intProperty: Int, var ownerProperty: String) : SimpleAbstractClass(stringProperty, intProperty), SimpleInterface {
    override fun funcInAbstract() {
        println("SimpleInheritanceClass funcInAbstract exec")
    }

    override fun funcInInterface() {
        println("SimpleInheritanceClass funcInInterface exec")
    }
}

// 继承抽象类和接口,使用次构造函数, 声明的时候都不能带主构造函数
class SimpleInheritanceConstructorClass : SimpleInheritanceClass {
    constructor(stringProperty: String, intProperty: Int, ownerProperty: String) : super(stringProperty, intProperty, ownerProperty)
}