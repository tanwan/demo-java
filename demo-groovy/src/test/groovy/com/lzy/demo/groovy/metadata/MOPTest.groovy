package com.lzy.demo.groovy.metadata

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * MOP(MetaObject Protocol),元对象协议,是Groovy语言中的一种协议,为元编程提供了优雅的解决方案
 * @author lzy
 */
class MOPTest {
    /**
     * Groovy拦截器,所有的方法(静态方法除外)调用都会统一路由到invokeMethod
     * See com.lzy.demo.groovy.metadata.SimpleMOP.groovy Groovy方法调用机制第1点
     */
    @Test
    void testInterceptable() {
        def simpleInterceptable = new SimpleInterceptable()
        assert simpleInterceptable.existingMethod() == 'invokeMethod'
        // 不存在的方法也会调用到invokeMethod
        assert simpleInterceptable.nonExistingMethod() == 'invokeMethod'
        assert simpleInterceptable.invokeMethod("existingMethod", null) == 'invokeMethod'
        assert simpleInterceptable.invokeMethod("nonExistingMethod", null) == 'invokeMethod'
        // 静态方法不受影响
        assert SimpleInterceptable.staticExistingMethod() == 'staticExistingMethod'
    }

    /**
     * MetaClass
     * See com.lzy.demo.groovy.metadata.SimpleMOP.groovy Groovy方法调用机制第2点,第3点
     */
    @Test
    void testMetaClass() {

        // 类的metaClass和对象的metaClass是两个不一样的实例,对象的metaClass是从类的metaClass复制出来的
        // 所以类的metaClass作用于在它修改之后创建的所有对象,而对象的metaClass只作用于当前对象

        def beforeMetaClassAdd = new SimpleGroovyObject()

        // 对类的metaClass添加方法
        SimpleGroovyObject.metaClass.methodExistInMetaClass = this.&methodExistInMetaClass
        // 对类的metaClass添加闭包
        SimpleGroovyObject.metaClass.closureExistInMetaClass = { 'metaClass Closure in Class' }

        def afterMetaClassAdd = new SimpleGroovyObject()

        // 对对象的metaClass添加闭包,只对当前对象有效果
        afterMetaClassAdd.metaClass.closureExistInMetaClassObject = { 'metaClass Closure in Object' }

        // beforeMetaClassAdd是在类的metaClass修改之前创建的,所以没有closureExistInMetaClass闭包
        Assertions.assertThrowsExactly(MissingMethodException, { beforeMetaClassAdd.closureExistInMetaClass() })
        assert afterMetaClassAdd.closureExistInMetaClass() == 'metaClass Closure in Class'

        // 只对当前对象有作用
        assert afterMetaClassAdd.closureExistInMetaClassObject() == 'metaClass Closure in Object'
        // 对其它对象无作用
        Assertions.assertThrowsExactly(MissingMethodException, { new SimpleGroovyObject().closureExistInMetaClassObject() })

        assert afterMetaClassAdd.methodExistInMetaClass() == 'metaClass Method'
        // 闭包定义在类中,所以可以直接调用
        assert new SimpleGroovyObject().cl() == 'cl'

        // 对于POJO, 同样也有metaClass
        beforeMetaClassAdd = new Integer(3)
        Integer.metaClass.closureExistInMetaClass = { 'metaClass Closure in Class' }
        afterMetaClassAdd = new Integer(23)
        afterMetaClassAdd.metaClass.closureExistInMetaClassObject = { 'metaClass Closure in Object' }
        // 跟POGO比较不一样的是, 在类的metaClass添加方法之前创建的也是有效果的
        assert beforeMetaClassAdd.closureExistInMetaClass() == 'metaClass Closure in Class'
        Assertions.assertThrowsExactly(MissingMethodException, { beforeMetaClassAdd.closureExistInMetaClassObject() })
        assert afterMetaClassAdd.closureExistInMetaClass() == 'metaClass Closure in Class'
        assert afterMetaClassAdd.closureExistInMetaClassObject() == 'metaClass Closure in Object'
    }

    /**
     * 只重写了methodMissing
     * See com.lzy.demo.groovy.metadata.SimpleMOP.groovy Groovy方法调用机制第4点
     */
    @Test
    void testClassWithMethodMissingOnly() {
        def simpleWithMethodMissingOnly = new SimpleWithMethodMissingOnly()
        assert simpleWithMethodMissingOnly.existingMethod() == 'existingMethod'
        assert simpleWithMethodMissingOnly.nonExistingMethod('args') == 'methodMissing'
        // 静态的methodMissing
        assert SimpleWithMethodMissingOnly.nonExistingMethod('static missing method') == '$static_methodMissing'
    }

    /**
     * 只重写了invokeMethod的POGO类
     * See com.lzy.demo.groovy.metadata.SimpleMOP.groovy Groovy方法调用机制第5点
     */
    @Test
    void testInvokeMethodOnly() {
        def simpleWithInvokeMethodOnly = new SimpleWithInvokeMethodOnly()
        assert simpleWithInvokeMethodOnly.existingMethod() == 'existingMethod'
        assert simpleWithInvokeMethodOnly.nonExistingMethod() == 'invokeMethod'

        // 调用了默认的invokeMethod,然后抛出MissingMethodException
        Assertions.assertThrowsExactly(MissingMethodException, { new SimpleGroovyObject().nonExistingMethod() })
    }

    /**
     * 访问属性和修改属性
     */
    @Test
    void testProperty() {
        def simpleWithGetProperty = new SimpleWithProperty()
        simpleWithGetProperty.property1
        // 属性都会在metaClass的attribute中
        assert simpleWithGetProperty.property1 == simpleWithGetProperty.metaClass.getAttribute(simpleWithGetProperty, 'property1')

        // 如果有propertyMissing方法,则会调用到此方法,没有则会抛出MissingPropertyException
        assert simpleWithGetProperty.noExistProperty == 'noExistProperty'

        // 静态的propertyMissing
        assert SimpleWithProperty.noExistProperty == '$static_propertyMissing'

        simpleWithGetProperty.property1 = 'property1 override'
        simpleWithGetProperty.metaClass.setAttribute(simpleWithGetProperty, 'property1', 'property1 metaClass override')
        // 可以直接在metaClass为属性赋值
        assert simpleWithGetProperty.property1 == 'property1 metaClass override'
        Assertions.assertThrowsExactly(MissingPropertyException, { simpleWithGetProperty.noExistProperty = 'noExistProperty override' })
    }

    def methodExistInMetaClass() {
        println('metaClass Method')
        'metaClass Method'
    }
}
