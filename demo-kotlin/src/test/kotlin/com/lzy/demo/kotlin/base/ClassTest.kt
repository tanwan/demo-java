package com.lzy.demo.kotlin.base

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
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
        assertEquals("string value", simpleClass.stringProperty)
        assertEquals(3, simpleClass.intProperty)

        // 使用次构造函数
        simpleClass = SimpleClass("string value")
        assertEquals("string value", simpleClass.stringProperty)
        assertEquals(0, simpleClass.intProperty)

        // 使用次构造函数
        simpleClass = SimpleClass(3)
        assertEquals("constructor default stringProperty", simpleClass.stringProperty)
        assertEquals(3, simpleClass.intProperty)

        // 使用次构造函数
        simpleClass = SimpleClass("string value", 3, "ownerProperty value")
        assertEquals("string value", simpleClass.stringProperty)
        assertEquals(3, simpleClass.intProperty)
        assertThat(simpleClass.listProperty).contains("ownerProperty value")
    }

    /**
     * getter,setter
     */
    @Test
    fun testGetterSetter() {
        val simpleClass = SimpleClass("string value", 3)
        simpleClass.getterSetter = "getterSetter override"
        assertEquals("use get use set getterSetter override", simpleClass.getterSetter)
    }

    /**
     * 继承
     */
    @Test
    fun testInheritance() {
        // 继承open类
        val inheritanceOpen = SimpleInheritanceOpenClass("string value", 3, "ownerProperty value")
        assertEquals("string value", inheritanceOpen.stringProperty)
        assertEquals(3, inheritanceOpen.intProperty)
        assertEquals("ownerProperty value", inheritanceOpen.ownerProperty)
        inheritanceOpen.finalFunc()
        inheritanceOpen.openFunc()

        // 继承抽象类和实现接口
        val inheritanceAbstract = SimpleInheritanceClass("string value", 3, "ownerProperty value")
        assertEquals("string value", inheritanceAbstract.stringProperty)
        assertEquals(3, inheritanceAbstract.intProperty)
        assertEquals("ownerProperty value", inheritanceAbstract.ownerProperty)

        inheritanceAbstract.funcInAbstract()
        inheritanceAbstract.funcInInterface()
        inheritanceAbstract.defaultFunc("str variable")

        // 使用次构造函数
        var inheritanceConstructor = SimpleInheritanceConstructorClass("string value", 3, "ownerProperty value")
        assertEquals("string value", inheritanceConstructor.stringProperty)
        assertEquals(3, inheritanceConstructor.intProperty)
        assertEquals("ownerProperty value", inheritanceConstructor.ownerProperty)

        inheritanceConstructor = SimpleInheritanceConstructorClass("string value", 3)
        assertEquals("string value", inheritanceConstructor.stringProperty)
        assertEquals(3, inheritanceConstructor.intProperty)
        assertEquals("default ownerProperty value", inheritanceConstructor.ownerProperty)

        // 实现带get的方法
        val overrideGetterMethod = OverrideGetterMethod("method1", "method2")
        assertEquals("method1", overrideGetterMethod.getMethod1())
        assertEquals("method2", overrideGetterMethod.getMethod2())
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
        assertEquals("delegate property", simpleDelegateProperty.value)
        assertEquals("delegate property", delegateProperty)

        // lazy是一个函数,接受一个Lambda表达式作为参数,返回一个Lazy类型的实例(只有getValue,所以不能使用var)
        // 实现延迟加载属性: 第一次调用getValue时,将会执行Lambda表达式,后面再执行getValue时,都返回前面的结果
        val lazyValue: String by lazy {
            println("lazy")
            "lazy value"
        }
        assertEquals("lazy value", lazyValue)

        // 可以观察属性的变动
        var observableValue: String by Delegates.observable("init value") { kProperty, oldName, newName ->
            println("kProperty：${kProperty.name} | oldName:$oldName | newName:$newName")
        }

        assertEquals("init value", observableValue)
        observableValue = "override"
        assertEquals("override", observableValue)
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

    /**
     * 获取class类
     */
    @Test
    fun testClass() {
        val simpleClass = SimpleClass("string value", 3)
        // ::class相当于getClass,不过获取的是kotlin.reflect.KClass
        assertEquals(SimpleClass::class, simpleClass::class)
        assertThat(simpleClass::class).asString().isEqualTo("class com.lzy.demo.kotlin.base.SimpleClass (Kotlin reflection is not available)")

        // ::class.java获取的是java的class
        assertEquals(SimpleClass::class.java, simpleClass::class.java)
        assertThat(simpleClass::class.java).asString().isEqualTo("class com.lzy.demo.kotlin.base.SimpleClass")
    }
}