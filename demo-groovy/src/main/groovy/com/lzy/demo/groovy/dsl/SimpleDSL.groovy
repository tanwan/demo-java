package com.lzy.demo.groovy.dsl


class SimpleDSL {

    static def simpleOuter(@DelegatesTo(SimpleOuterDSL) Closure cl) {
        def simpleOuterDSL = new SimpleOuterDSL()
        cl.resolveStrategy = Closure.DELEGATE_ONLY
        cl.delegate = simpleOuterDSL
        cl()
        simpleOuterDSL.finallyFunc('SimpleDSL exec finish')
    }
}

class SimpleOuterDSL {

    def outerProp
    def simpleMiddleDSL

    def simpleMiddle(@DelegatesTo(SimpleMiddleDSL) Closure cl) {
        simpleMiddleDSL = new SimpleMiddleDSL()
        cl.resolveStrategy = Closure.DELEGATE_ONLY
        cl.delegate = simpleMiddleDSL
        cl()
    }

    def execFunc(def variable) {
        println("execFunc,variable:$variable")
    }

    def finallyFunc(def variable) {
        println("variable:$variable,outerProp:$outerProp,middleProp:$simpleMiddleDSL.middleProp,innerProp:$simpleMiddleDSL.simpleInnerDSL.innerProp")
    }
}

class SimpleMiddleDSL {
    def middleProp
    def simpleInnerDSL

    def simpleInner(@DelegatesTo(SimpleInnerDSL) Closure cl) {
        simpleInnerDSL = new SimpleInnerDSL()
        cl.resolveStrategy = Closure.DELEGATE_ONLY
        cl.delegate = simpleInnerDSL
        cl()
    }
}

class SimpleInnerDSL {
    def innerProp
}

