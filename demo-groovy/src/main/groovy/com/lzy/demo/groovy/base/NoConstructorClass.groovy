package com.lzy.demo.groovy.base

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 *  不带修饰符的字段会当成属性(带有getter,setter方法)
 *
 * @author lzy
 * @version v1.0
 */
@ToString(includePackage = false)
@EqualsAndHashCode
class NoConstructorClass {


    def stringProperty

    Integer intProperty

}
