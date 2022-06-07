package com.lzy.demo.groovy.metadata

/**
 * MOP
 * @author lzy
 */
/**
 * Groovy有3种对象:
 * 1. POJO(普通Java对象)
 * 2. POGO(Groovy编写的对象)Groovy的所有类都实现了GroovyObject
 * 3. Groovy拦截器(实现了GroovyInterceptable的GroovyObject)
 *
 * Groovy方法调用机制
 * 1. 如果是Groovy拦截器,那么所有的方法(静态方法除外)调用都会统一路由到invokeMethod(它是GroovyObject提供的方法,可以重写)
 * 2. 判断方法是否在MetaClass或者Class中,如果存在,则直接调用
 * 3. 否则判断属性是否在MetaClass或者Class中,如果该属性是个闭包,则直接调用此闭包
 * 4. 否则判断是否有methodMissing方法,如果有,直接调用
 * 5. 否则调用invokeMethod(可以重写),默认使用MetaClass执行,失败则抛出MissingMethodException
 *
 * Groovy属性调用机制
 * 访问属性都会调用到getProperty(可以重写)
 * 修改属性都会调用到setProperty(可以重写)
 */


/**
 * Groovy拦截器,@See org.codehaus.groovy.runtime.callsite.CallSiteArray#createPogoSite
 */
class SimpleInterceptable implements GroovyInterceptable {

    /**
     * 静态方法不受影响
     */
    static def staticExistingMethod() {
        println('SimpleInterceptable#staticExistingMethod')
        'staticExistingMethod'
    }

    def existingMethod() {
        println('SimpleInterceptable#existingMethod')
        'existingMethod'
    }

    /**
     * 这是一个Groovy拦截器,所有的方法(静态方法除外)调用都会统一路由到invokeMethod
     */
    def invokeMethod(String name, Object args) {
        // 这边不能使用println,会栈溢出,所以使用System.out.println
        System.out.println("SimpleInterceptable#invokeMethod,name:$name,args:$args")
        'invokeMethod'
    }
}


/**
 * 重写了invokeMethod的POGO类
 */
class SimpleWithInvokeMethodOnly {
    def existingMethod() {
        System.out.println('SimpleWithInvokeOnly#existingMethod')
        'existingMethod'
    }

    def invokeMethod(String name, args) {
        System.out.println("SimpleWithInvokeOnly#invokeMethod,name:$name,args:$args")
        'invokeMethod'
    }
}

/**
 * 简单的Groovy类
 */
class SimpleGroovyObject {
    def existingMethod() {
        System.out.println('SimpleGroovyObject#existingMethod')
        'existingMethod'
    }

    def cl = {
        System.out.println('SimpleGroovyObject#cl')
        'cl'
    }
}


/**
 * 重写了methodMissing的POGO
 */
class SimpleWithMethodMissingOnly {
    def existingMethod() {
        System.out.println('SimpleWithMethodMissingOnly#existingMethod')
        'existingMethod'
    }

    def methodMissing(String name, args) {
        System.out.println("SimpleWithMethodMissingOnly#methodMissing,name:$name,args:$args")
        'methodMissing'
    }

    /**
     * $static_methodMissing用来处理静态的methodMissing
     */
    static def $static_methodMissing(String name, Object args) {
        System.out.println("SimpleWithMethodMissingOnly#\$static_methodMissing,name:$name,args:$args")
        '$static_methodMissing'
    }
}

/**
 * 重写setProperty和getProperty方法
 */
class SimpleWithProperty {
    def property1 = 'property1 value'

    /**
     * 获取属性都会执行此方法
     */
    def getProperty(String propertyName) {
        println "SimpleWithProperty#getProperty: propertyName:$propertyName"
        return metaClass.getProperty(this, propertyName)
    }

    /**
     * 修改属性都会执行此方法
     */
    void setProperty(String propertyName, Object newValue) {
        println "SimpleWithProperty#setProperty: propertyName:$propertyName,newValue:$newValue"
        getMetaClass().setProperty(this, propertyName, newValue);
    }

    /**
     * 如果存在propertyMissing方法,那么访问不存在的属性则会调用到这里,否则抛出MissingPropertyException
     */
    def propertyMissing(String propertyName) {
        println "SimpleWithProperty#propertyMissing: propertyName:$propertyName"
        propertyName
    }

    /**
     * $static_propertyMissing: 用来处理静态的propertyMissing
     */
    static def $static_propertyMissing(String propertyName) {
        println "SimpleWithProperty#\$static_propertyMissing: propertyName:$propertyName"
        '$static_propertyMissing'
    }
}

