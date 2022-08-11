package com.lzy.demo.design.pattern.observer;

public interface Subject {

    void attach(Observer observer);

    void notifyAllObservers();
}
