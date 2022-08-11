package com.lzy.demo.design.pattern.bridge;

/**
 * 具体实现化角色
 *
 * @author LZY
 * @version v1.0
 */
public class ConcreteImplementor implements Implementor {

    @Override
    public void operationImpl() {
        System.out.println("ConcreteImplementor#operationImpl()");
    }
}
