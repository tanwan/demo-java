package com.lzy.demo.groovy.script

import groovy.grape.Grape



// 每个脚本都是一个groovy.lang.Script对象,所以可以直接调用它的方法,也可以扩展它

// 这边就是调用groovy.lang.Script的println方法
println('call groovy.lang.Script#println')


// 每个Script有一个Binding对象,用来传递参数,这个特性可以在运行时调用Script传递参数
getBinding().setVariable('simpleBinding', [k1: 'v1'])

// 在binding中设置的参数,可以直接使用
println("binding: $simpleBinding")

// 可以直接引入依赖,方便脚本引用其它依赖,在idea运行的话,需要在gradle中添加ive的依赖和编译时的依赖
@Grab(group = 'org.apache.commons', module = 'commons-lang3', version = '3.12.0')
import org.apache.commons.lang3.StringUtils
println StringUtils.isNotBlank('str')

