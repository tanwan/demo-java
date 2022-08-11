package com.lzy.demo.design.pattern.mediator;

public abstract class Colleague {

    protected Mediator mediator;

    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }

    public abstract void update();

    public abstract void changed();
}
