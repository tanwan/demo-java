package com.lzy.demo.groovy.dsl


// 这边是groovy后缀,也可以使用gdsl后缀

// 这边是直接调用SimpleDSL.simpleOuter
SimpleDSL.simpleOuter {
    outerProp = 'outerProp value'

    execFunc 'variable value'

    println('simpleOuter exec')

    simpleMiddle {
        middleProp = 'middleProp value'

        println('simpleMiddle exec')

        simpleInner {
            innerProp = 'innerProp value'

            println('simpleInner exec')
        }
    }
}

// 这边扩展了Script方法,See com.lzy.demo.groovy.dsl.ScriptExtension
simpleOuter {
    outerProp = 'outerProp value'

    execFunc 'variable value'

    println('simpleOuter exec')

    simpleMiddle {
        middleProp = 'middleProp value'

        println('simpleMiddle exec')

        simpleInner {
            innerProp = 'innerProp value'

            println('simpleInner exec')
        }
    }
}


// 在binding设置的变量,可以直接在脚本中使用
simpleOuterDSL.finallyFunc('SimpleDSLScript.groovy exec finish')

// 还可以动态的创建DSL
metaClass.dynamicDSL = { cl ->
    cl.resolveStrategy = Closure.DELEGATE_ONLY
    cl.delegate = getBinding().getVariable('simpleOuterDSL')
    cl()
}

dynamicDSL {
    execFunc 'dynamic variable value'
}