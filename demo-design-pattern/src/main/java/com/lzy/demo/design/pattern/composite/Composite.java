package com.lzy.demo.design.pattern.composite;

import java.util.List;

public class Composite {
    private String name;

    private List<Composite> subComposites;

    public Composite(String name) {
        this.name = name;
    }

    public Composite(String name, List<Composite> subComposites) {
        this.name = name;
        this.subComposites = subComposites;
    }

    @Override
    public String toString() {
        return "Composite{" +
                "name='" + name + '\'' +
                ", subComposites=" + subComposites +
                '}';
    }
}
