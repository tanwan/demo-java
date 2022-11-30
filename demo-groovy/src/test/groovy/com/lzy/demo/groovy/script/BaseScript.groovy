package com.lzy.demo.groovy.script


// 每个脚本都是一个groovy.lang.Script对象,所以可以直接调用它的方法,也可以扩展它

// 这边就是调用groovy.lang.Script的println方法
println('call groovy.lang.Script#println')


// 每个Script有一个Binding对象,用来传递参数,这个特性可以在运行时调用Script传递参数
getBinding().setVariable('simpleBinding', [k1: 'v1'])

// 在binding中设置的参数,可以直接使用
println("binding: $simpleBinding")

