package com.lzy.demo.design.pattern.mediator;

public class ConcreteColleague2 extends Colleague {

    public ConcreteColleague2(Mediator mediator) {
        super(mediator);
    }

    @Override
    public void update() {
        System.out.println("ConcreteColleague2 update");
    }

    @Override
    public void changed() {
        System.out.println("ConcreteColleague2 changed");
        mediator.operation(this);
    }
}
