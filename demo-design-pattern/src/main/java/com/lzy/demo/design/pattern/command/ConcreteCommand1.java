package com.lzy.demo.design.pattern.command;

public class ConcreteCommand1 implements Command {

    private Receiver1 receiver1;

    public ConcreteCommand1(Receiver1 receiver1) {
        this.receiver1 = receiver1;
    }

    @Override
    public void execute() {
        receiver1.operation1();
        receiver1.operation2();
    }
}
