package com.lzy.demo.design.pattern.visitor;

public class ConcreteElement2 implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
