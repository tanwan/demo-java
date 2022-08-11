package com.lzy.demo.design.pattern.mediator;

public class ConcreteMediator extends Mediator {
    @Override
    public void operation(Colleague colleague) {
        colleagues.stream().filter(t -> t != colleague).forEach(Colleague::update);
    }
}
