package com.lzy.demo.design.pattern.visitor;

public class ConcreteElement1 implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
