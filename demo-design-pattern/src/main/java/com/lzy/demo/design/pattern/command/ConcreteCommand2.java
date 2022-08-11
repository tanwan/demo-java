package com.lzy.demo.design.pattern.command;

public class ConcreteCommand2 implements Command {

    private Receiver2 receiver2;

    public ConcreteCommand2(Receiver2 receiver2) {
        this.receiver2 = receiver2;
    }

    @Override
    public void execute() {
        receiver2.operation1();
        receiver2.operation2();
    }
}
