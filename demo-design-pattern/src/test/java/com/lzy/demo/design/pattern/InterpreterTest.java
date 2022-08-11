package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.interpreter.Context;
import com.lzy.demo.design.pattern.interpreter.Expression;
import com.lzy.demo.design.pattern.interpreter.NonterminalExpression1;
import com.lzy.demo.design.pattern.interpreter.NonterminalExpression2;
import com.lzy.demo.design.pattern.interpreter.TerminalExpression1;
import com.lzy.demo.design.pattern.interpreter.TerminalExpression2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 测试解析器
 *
 * @author lzy
 * @version v1.0
 */
public class InterpreterTest {

    /**
     * 测试解析器
     */
    @Test
    public void testInterpreter() {
        Expression expression1 = new TerminalExpression1();
        Expression expression2 = new TerminalExpression2();
        Expression nonterminalExpression1 = new NonterminalExpression1(expression1, expression2);
        Expression nonterminalExpression2 = new NonterminalExpression2(expression1, expression2);

        assertTrue(nonterminalExpression1.interpret(new Context("true false")));
        assertTrue(nonterminalExpression2.interpret(new Context("true")));
    }
}
