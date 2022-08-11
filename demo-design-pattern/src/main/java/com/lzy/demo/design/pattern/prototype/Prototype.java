package com.lzy.demo.design.pattern.prototype;

public interface Prototype {
    <T extends Prototype> T copy();
}
