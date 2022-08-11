package com.lzy.demo.design.pattern.state;

public class ConcreteState2 implements State {
    @Override
    public void doAction(Context context) {
        System.out.println("ConcreteState2 doAction");
        context.setState(this);
    }
}
