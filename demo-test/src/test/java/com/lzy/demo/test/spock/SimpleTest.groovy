package com.lzy.demo.test.spock

import com.lzy.demo.test.bean.SimpleBean
import com.lzy.demo.test.serivce.DependenceService
import spock.lang.Specification


/**
 * spock简单测试,继承Specification
 *
 * @author lzy
 * @version v1.0
 */
class SimpleTest extends Specification {

    /**
     * 测试
     */
    def 'simple test'() {

        //准备数据
        given:
        def stack = new Stack()
        def elem = "push me"

        //执行测试方法
        when:
        stack.push(elem)

        //判断结果
        then:
        !stack.isEmpty()
        stack.size() == 1
        stack.peek() == elem

        //类似then,只是比then受限制
        expect:
        stack.isEmpty()

        //清理
        cleanup:
        println('method cleanup')
    }

    /**
     * 测试抛出异常
     */
    def 'test exception'() {
        given:
        def stack = new Stack()

        when:
        stack.pop()

        then:
        def e = thrown(EmptyStackException)
        stack.isEmpty()
    }

    /**
     * 测试where
     */
    def 'test where'() {
        expect:
        Math.max(a, b) == c

        where:
        //执行两次测试,第一次 a=5,b=1,c=5,第二次a=3,b=9,c=9
        a << [5, 3]
        b << [1, 9]
        c << [5, 9]
    }

    /**
     * 测试mock
     */
    def 'test mock'() {
        given:
        def mockService = Mock(DependenceService)
        def stubService = Stub(DependenceService)
        def simpleBean = new SimpleBean("mock")
        def simpleBean2 = new SimpleBean("mock2")
        mockService.dependenceMethod(simpleBean2) >> new SimpleBean("mock2")
        mockService.dependenceMethod(_) >> new SimpleBean("mock")
        stubService.dependenceMethod(_) >> new SimpleBean("stub")

        when:
        def mockResult = mockService.dependenceMethod(simpleBean)
        def mockResult2 = mockService.dependenceMethod(simpleBean2)
        def stubResult = stubService.dependenceMethod(simpleBean)

        then:
        mockResult.getBody() == 'mock'
        mockResult2.getBody() == 'mock2'
        stubResult.getBody() == 'stub'
    }

    /**
     * 在第一个测试方法执行之前
     */
    def setupSpec() {
        println('setupSpec')
    }
    /**
     * 在每一个测试方法执行之前
     */
    def setup() {
        println('setup')
    }
    /**
     * 在最后一个测试方法执行完之后
     */
    def cleanup() {
        println('cleanup')
    }
    /**
     * 在每一个测试方法执行完之后
     */
    def cleanupSpec() {
        println('cleanupSpec')
    }

}
