package com.lzy.demo.kotlin.base


/**
 *
 * 委托,其实就是一个代理
 *
 * @author lzy
 * @version v1.0
 */
// 调用的方法交给delegate去处理
class SimpleDelegate(delegate: SimpleInterface) : SimpleInterface by delegate {

    override fun defaultFunc(str: String) {
        println("SimpleDelegationClass defaultFunc exec,str:$str")
    }

}
