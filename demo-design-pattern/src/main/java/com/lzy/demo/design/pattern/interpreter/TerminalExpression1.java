package com.lzy.demo.design.pattern.interpreter;

public class TerminalExpression1 implements Expression {
    @Override
    public boolean interpret(Context context) {
        return context.getData().contains("true");
    }
}
