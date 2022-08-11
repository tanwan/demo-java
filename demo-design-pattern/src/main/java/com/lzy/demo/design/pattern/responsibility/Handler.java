package com.lzy.demo.design.pattern.responsibility;

public abstract class Handler {
    private Handler nextHandler;

    protected abstract void operation();

    public void handler() {
        operation();
        if (nextHandler != null) {
            nextHandler.operation();
        }
    }

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
