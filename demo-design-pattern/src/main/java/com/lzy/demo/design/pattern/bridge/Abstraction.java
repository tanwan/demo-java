package com.lzy.demo.design.pattern.bridge;

/**
 * 抽象化角色
 * 将抽象部分与它的实现部分相分离,使它们都可以独立地变化
 * 本质上是使用组合来代替继承
 *
 * @author LZY
 * @version v1.0
 */
public abstract class Abstraction {
    private Implementor implementor;

    public Abstraction(Implementor implementor) {
        this.implementor = implementor;
    }

    /**
     * 操作
     */
    public void operation() {
        implementor.operationImpl();
    }

    public Implementor getImplementor() {
        return implementor;
    }
}
