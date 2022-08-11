package com.lzy.demo.design.pattern.state;

public class ConcreteState1 implements State {
    @Override
    public void doAction(Context context) {
        System.out.println("ConcreteState1 doAction");
        context.setState(this);
    }
}
