package com.lzy.demo.groovy.dsl

import org.junit.jupiter.api.Test
import org.reflections.Reflections

class DSLUseScriptTest {

    /**
     * 使用GroovyShell脚本执行dsl
     */
    @Test
    void testDSLUseGroovyShell() {
        def binding = new Binding()
        def simpleOuterDSL = new SimpleOuterDSL()
        binding.setVariable("simpleOuterDSL", simpleOuterDSL)
        GroovyShell shell = new GroovyShell(binding)
        def scriptContent = """
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
        simpleOuterDSL.finallyFunc('in script file exec finish')
        """
        // 直接执行groovy脚本,也可以指定文件
        // evaluate方法只能执行脚本,run方法可以执行脚本,main或者测试
        shell.run(scriptContent, 'testFile')
        simpleOuterDSL.finallyFunc('testDslUseGroovyShell')
    }

    /**
     * 使用Reflections执行dsl,本质是执行已经编译好的Script类
     */
    @Test
    void testDSLUseReflections() {
        Set<Class<? extends Script>> scripts = new Reflections("com.lzy.demo.groovy.dsl").getSubTypesOf(Script.class)
        def binding = new Binding()
        def simpleOuterDSL = new SimpleOuterDSL()
        binding.setVariable("simpleOuterDSL", simpleOuterDSL)
        for (Class<? extends Script> script : scripts) {
            def scriptInstance = script.getDeclaredConstructor(Binding.class).newInstance(binding)
            scriptInstance.run()
        }
    }
}
