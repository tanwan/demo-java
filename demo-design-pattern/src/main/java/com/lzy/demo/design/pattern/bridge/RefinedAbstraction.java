package com.lzy.demo.design.pattern.bridge;

/**
 * 修正抽象化角色
 *
 * @author LZY
 * @version v1.0
 */
public class RefinedAbstraction extends Abstraction {

    public RefinedAbstraction(Implementor implementor) {
        super(implementor);
    }

    /**
     * 操作
     */
    @Override
    public void operation() {
        System.out.println("refinedAbstraction do something");
        getImplementor().operationImpl();
    }

}
