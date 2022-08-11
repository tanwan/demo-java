package com.lzy.demo.design.pattern.interpreter;

public class NonterminalExpression1 implements Expression {

    private Expression leftExpression;

    private Expression rightExpression;

    public NonterminalExpression1(Expression leftExpression, Expression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public boolean interpret(Context context) {
        return leftExpression.interpret(context) && rightExpression.interpret(context);
    }
}
