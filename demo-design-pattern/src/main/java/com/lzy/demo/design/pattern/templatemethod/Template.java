package com.lzy.demo.design.pattern.templatemethod;

/**
 * 模板方法
 * 定义一个操作中基本的框架,把一些步骤延迟到子类中
 *
 * @author LZY
 * @version v1.0
 */
public abstract class Template {

    public final void templateMethod() {
        //这里固定调用的顺序逻辑
        this.primitiveOperation1();
        this.primitiveOperation2();
        //钩子函数 if(this.hook())
        this.hook();
    }


    protected abstract void primitiveOperation1();

    protected abstract void primitiveOperation2();


    /**
     * 钩子函数(可选),可以对模板方法进行扩展,比如可以用来要不要执行某一个操作
     * 为空实现
     */
    protected void hook() {

    }
}
