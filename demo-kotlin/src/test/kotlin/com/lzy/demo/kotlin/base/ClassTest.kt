package com.lzy.demo.kotlin.base

import org.junit.jupiter.api.Test
import kotlin.properties.Delegates

class ClassTest {

    /**
     * 构造函数
     */
    @Test
    fun testConstructor() {
        // 创建实例不用new,使用主构造函数
        var simpleClass = SimpleClass("string value", 3)
        println("simpleClass main constructor:${simpleClass.stringProperty},${simpleClass.intProperty}")

        // 使用次构造函数
        simpleClass = SimpleClass("string value")
        println("simpleClass constructor1:${simpleClass.stringProperty},${simpleClass.intProperty}")

        // 使用次构造函数
        simpleClass = SimpleClass(3)
        println("simpleClass constructor2:${simpleClass.stringProperty},${simpleClass.intProperty}")

        // 使用次构造函数
        simpleClass = SimpleClass("string value", 3, "ownerProperty value")
        println("simpleClass constructor3:${simpleClass.stringProperty},${simpleClass.intProperty},${simpleClass.listProperty}")
    }

    /**
     * getter,setter
     */
    @Test
    fun testGetterSetter() {
        val simpleClass = SimpleClass("string value", 3)
        simpleClass.getterSetter = "getterSetter override"
        println("simpleClass:${simpleClass.getterSetter}")
    }

    /**
     * 继承
     */
    @Test
    fun testInheritance() {
        // 继承open类
        val inheritanceOpen = SimpleInheritanceOpenClass("string value", 3, "ownerProperty value")
        println("inheritanceOpen:${inheritanceOpen.stringProperty},${inheritanceOpen.intProperty},${inheritanceOpen.ownerProperty}")
        inheritanceOpen.finalFunc()
        inheritanceOpen.openFunc()

        // 继承抽象类和实现接口
        val inheritanceAbstract = SimpleInheritanceClass("string value", 3, "ownerProperty value")
        println("inheritanceAbstract:${inheritanceAbstract.stringProperty},${inheritanceAbstract.intProperty},${inheritanceAbstract.ownerProperty}")
        inheritanceAbstract.funcInAbstract()
        inheritanceAbstract.funcInInterface()
        inheritanceAbstract.defaultFunc("str variable")

        // 使用次构造函数
        val inheritanceConstructor = SimpleInheritanceConstructorClass("string value", 3, "ownerProperty value")
        println("inheritanceConstructor:${inheritanceConstructor.stringProperty},${inheritanceConstructor.intProperty},${inheritanceConstructor.ownerProperty}")
    }

    /**
     * data class
     */
    @Test
    fun testDataClass() {
        val dataClass = SimpleDataClass(3, "string value")
        dataClass.bodyProperty = "string value"

        // 默认提供了toString,不过在class body声明的属性没有在toString中
        println("dataClass:$dataClass,stringProperty:${dataClass.bodyProperty}")

        // copy
        var copy = dataClass.copy()
        println("copy:$copy,bodyProperty:${copy.bodyProperty}")
        // copy可以传入参数覆盖原有的值
        copy = dataClass.copy(intProperty = 1)
        println("copy:$copy,bodyProperty:${copy.bodyProperty}")
    }


    /**
     * object,也就是单例
     */
    @Test
    fun testObject() {
        // 不用创建实例就可以直接调用函数
        SimpleObject.func()
    }

    /**
     * 其实就是代理
     */
    @Test
    fun testDelegate() {
        val inheritanceAbstract = SimpleInheritanceClass("string value", 3, "ownerProperty value")
        val delegate = SimpleDelegate(inheritanceAbstract)
        delegate.defaultFunc("string value")
        delegate.funcInInterface()
    }

    /**
     * 委托属性
     */
    @Test
    fun testDelegateProperty() {
        val simpleDelegateProperty = SimpleDelegateProperty()
        // var要求委托属性需要有getValue和setValue方法,val只要有setValue方法
        var delegateProperty: String by simpleDelegateProperty

        delegateProperty = "delegate property"
        // 给delegateProperty赋值,也相当于为simpleDelegateProperty.value赋值
        println("simpleDelegateProperty:${simpleDelegateProperty.value}")
        println("delegateProperty:$delegateProperty")


        // lazy是一个函数,接受一个Lambda表达式作为参数,返回一个Lazy类型的实例(只有getValue,所以不能使用var)
        // 实现延迟加载属性: 第一次调用getValue时,将会执行Lambda表达式,后面再执行getValue时,都返回前面的结果
        val lazyValue: String by lazy {
            println("lazy")
            "lazy value"
        }

        println("lazyValue:$lazyValue")


        // 可以观察属性的变动
        var observableValue: String by Delegates.observable("init value") { kProperty, oldName, newName ->
            println("kProperty：${kProperty.name} | oldName:$oldName | newName:$newName")
        }

        println("observableValue init:$observableValue")
        observableValue = "override"
        println("observableValue after:$observableValue")
    }


    /**
     * 伴生对象
     */
    @Test
    fun testCompanion() {
        // 可以直接使用伴生对象的属性
        println(SimpleCompanion.property)
        // 可以直接调用伴生对象的方法
        SimpleCompanion.companionFunc()
    }

    @Test
    fun testClass() {
        val simpleClass = SimpleClass("string value", 3)
        // ::class相当于getClass,不过获取的是kotlin.reflect.KClass
        println("class:${simpleClass::class}")
    }
}