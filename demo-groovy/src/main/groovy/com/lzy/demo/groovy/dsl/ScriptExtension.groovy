package com.lzy.demo.groovy.dsl

/**
 *
 * @author lzy
 * @version v1.0
 */
class ScriptExtension {

    /**
     * 用来扩展script
     */
    static def simpleOuter(Script self, @DelegatesTo(SimpleOuterDSL) Closure cl) {
        def simpleOuterDSL
        if (self.getBinding().hasVariable('simpleOuterDSL')) {
            simpleOuterDSL = self.getBinding().getVariable('simpleOuterDSL')
        } else {
            simpleOuterDSL = new SimpleOuterDSL()
            self.getBinding().setVariable('simpleOuterDSL', simpleOuterDSL)
        }
        cl.resolveStrategy = Closure.DELEGATE_ONLY
        cl.delegate = simpleOuterDSL
        cl()
    }
}
