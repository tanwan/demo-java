package com.lzy.demo.design.pattern.mediator;

public class ConcreteColleague1 extends Colleague {

    public ConcreteColleague1(Mediator mediator) {
        super(mediator);
    }

    @Override
    public void update() {
        System.out.println("ConcreteColleague1 update");
    }

    @Override
    public void changed() {
        System.out.println("ConcreteColleague1 changed");
        mediator.operation(this);
    }
}
