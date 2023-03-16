package com.lzy.demo.kotlin.base


// 主构造函数,constructor可以省略,如果constructor需要添加修改符(private),则constructor不能省略
class SimpleClass constructor(val stringProperty: String, val intProperty: Int) {

    // 属性默认是public的,也可以添加修改符

    // 这边是可以拿到构造函数传进来的值的,val默认实现getter
    val listProperty: MutableList<String> = mutableListOf(stringProperty)

    // 属性都必须赋值,如果需要赋值为null,需要使用?, var默认实现getter,setter
    var defaultNullProperty: String? = null

    // 显示声明getter setter
    var getterSetter: String = ""
        // 方法体可以省略, field表示属性
        get() = "use get $field"
        // 使用方法体
        set(value) {
            field = "use set $value"
        }

    // init块在创建实例的时候执行
    init {
        println("SimpleClass init1 exec,stringProperty:$stringProperty,intProperty:$intProperty,listProperty:$listProperty")
    }

    // 可以有多个init,按顺序执行
    init {
        println("SimpleClass init2 exec,stringProperty:$stringProperty,intProperty:$intProperty,listProperty:$listProperty")
    }

    // 使用constructor声明次构造函数
    constructor(stringProperty: String) : this(stringProperty, 0)

    // 次构造函数可以有方法体
    constructor(intProperty: Int) : this("constructor default stringProperty", intProperty) {
        println("SimpleClass constructor exec,intProperty:$intProperty")
    }

    // 次构造函数的参数不能有val/var,所以不能声明属性
    constructor(stringProperty: String, intProperty: Int, ownerProperty: String) : this(stringProperty, intProperty) {
        listProperty.add(ownerProperty)
    }
}