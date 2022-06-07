package com.lzy.demo.groovy.metadata

/**
 * 元编程
 *
 * 在META-INFO/org.codehaus.groovy.runtime.ExtensionModule创建被扩展类全类名的文件
 *
 * @author lzy
 * @version v1.0
 */
class SimpleExtension {

    /**
     * 扩展方法
     * @param self 这个参数表示要扩展的类
     * @param var 方法的入参
     * @return str
     */
    static def extensionFunc(String self, String variable) {
        return "extensionFunc,variable:$variable"
    }
}

class SimpleStaticExtension {


    /**
     * 扩展静态方法
     * @param self 这个参数表示要扩展的类
     * @param var
     * @return str
     */
    static def extensionStaticFunc(String self, def variable) {
        return "extensionStaticFunc,variable:$variable"
    }
}