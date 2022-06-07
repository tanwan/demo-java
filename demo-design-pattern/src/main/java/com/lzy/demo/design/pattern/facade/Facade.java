package com.lzy.demo.design.pattern.facade;

/**
 * 外观模式
 * 封装了子系统内部多个模块的交互过程,从而简化了外部的调用
 *
 * @author LZY
 * @version v1.0
 */
public class Facade {
    private Module1 module1 = new Module1();
    private Module2 module2 = new Module2();

    /**
     * 外观模式方法
     */
    public void method() {
        module1.method();
        module2.method();
    }
}
