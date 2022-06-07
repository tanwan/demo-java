package com.lzy.demo.groovy.base

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/**
 * 闭包
 *
 * @author lzy
 * @version v1.0
 */
class ClosureTest {
    def name = initName()
    def closureTestOnly = 'closureTestOnly'

    /**
     * 闭包基础用法
     */
    @Test
    void testBase() {
        // 每个闭包都是Closure
        Closure cl = { parameter -> println("parameter:$parameter") }
        // 使用()调用闭包
        cl('parameter value')
        // 使用call调用闭包
        cl.call('use call: parameter value')

        // 闭包如果只有一个入参,则可以省略,it就是入参
        cl = { println("parameter:$it") }
        cl('use it:parameter value')

        // this: 声明该闭包的对象(实例闭包)或者类(静态闭包)
        // owner: 声明该闭包的对象(同this)或者声明它的闭包(闭包在其它闭包声明)
        // delegate: 没有修改时,同owner一样,但是delegate可以修改
        cl = {
            println("default,this:${this.getClass()},owner:${owner.getClass()},delegate:${delegate.getClass()}")
        }
        cl.call()

        // 这边把delegate修改成一个list
        cl = {
            println("change delegate,this:${this.getClass()},owner:${owner.getClass()},delegate:${delegate.getClass()}")
        }
        cl.delegate = []
        cl.call()

        def outerClosure = {
            def innerClosure = {
                // 这个闭包在其它闭包中声明,所以owner和delegate就是外层的闭包
                println("innerClosure,this:${this.getClass()},owner:${owner.getClass()},delegate:${delegate.getClass()}")
            }
            innerClosure.call()
        }
        outerClosure.call()
    }

    /**
     * 解析策略
     */
    @Test
    void testResolveStrategy() {
        // Closure.OWNER_FIRST(默认),先在owner上解析,然后在delegate解析
        def data = [name: 'testResolveStrategy', dataOnly: 'dataOnly']
        def cl = {
            println("strategy:$it,name:$name,closureTestOnly:$closureTestOnly,dataOnly:$dataOnly")
            // 可以修改值
            name += ' override'
        }
        cl.delegate = data
        cl.call('Closure.OWNER_FIRST')
        // name的值在闭包修改了
        assert name == 'ClosureTest override'
        assert data.name == 'testResolveStrategy'

        initName()
        // OWNER_ONLY,只在own解析
        cl.resolveStrategy = Closure.OWNER_ONLY
        // 这边获取不到属性会抛异常
        Assertions.assertThatCode(() -> cl.call('Closure.OWNER_ONLY'))
                .isInstanceOf(MissingPropertyException.class)

        initName()
        // Closure.DELEGATE_FIRST,先在delegate解析,然后在owner解析,不过这边目前是有问题的,表现跟Closure.DELEGATE_ONLY是一样的
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl.call('Closure.DELEGATE_FIRST')
        assert name == 'ClosureTest'
        // name的值在闭包修改了
        assert data.name == 'testResolveStrategy override'

        initName()
        // Closure.DELEGATE_ONLY,只在delegate解析
        cl.resolveStrategy = Closure.DELEGATE_ONLY
        cl.call('Closure.DELEGATE_ONLY')
    }

    /**
     * 注解:@DelegatesTo
     */
    @Test
    void testAnnotation() {
        delegatesToAnnotation {
            println("name:$name,dataOnly:$dataOnly")
        }
    }

    /**
     * 注解@DelegatesTo对运行时没有作用,它是为了让开发者知道需要传什么类型的闭包
     * @param cl closure
     */
    def delegatesToAnnotation(@DelegatesTo(value = Map, strategy = Closure.DELEGATE_ONLY) Closure cl) {
        def data = [name: 'testResolveStrategy', dataOnly: 'dataOnly']
        cl.delegate = data
        cl.resolveStrategy = Closure.DELEGATE_ONLY
        cl()
    }


    def initName() {
        name = 'ClosureTest'
    }
}
