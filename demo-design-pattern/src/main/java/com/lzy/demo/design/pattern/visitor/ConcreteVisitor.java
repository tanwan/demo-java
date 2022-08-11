package com.lzy.demo.design.pattern.visitor;

public class ConcreteVisitor implements Visitor {
    @Override
    public void visit(ConcreteElement1 concreteElement1) {
        System.out.println("concreteElement1 visit");
    }

    @Override
    public void visit(ConcreteElement2 concreteElement2) {
        System.out.println("concreteElement2 visit");
    }
}
