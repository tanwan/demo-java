package com.lzy.demo.design.pattern.iterator;

public interface Aggregate<T> {
    Iterator<T> getIterator();
}
