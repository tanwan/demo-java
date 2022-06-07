package com.lzy.demo.design.pattern.templatemethod;

/**
 * 具体的模板方法
 *
 * @author LZY
 * @version v1.0
 */
public class ConcreteTemplate extends Template {
    /**
     * 操作步骤1
     */
    @Override
    protected void primitiveOperation1() {
        System.out.println("operation 1");
    }

    /**
     * 操作步骤2
     */
    @Override
    protected void primitiveOperation2() {
        System.out.println("operation 2");
    }

    /**
     * 钩子函数(可选),可以对模板方法进行扩展,比如可以用来要不要执行某一个操作
     * 为空实现
     */
    @Override
    protected void hook() {
        System.out.println("hook");
    }
}
