package com.lzy.demo.design.pattern.mediator;

import java.util.ArrayList;
import java.util.List;

public abstract class Mediator {
    protected List<Colleague> colleagues = new ArrayList<>();

    public void add(Colleague colleague) {
        colleagues.add(colleague);
    }

    public abstract void operation(Colleague colleague);
}
