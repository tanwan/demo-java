package com.lzy.demo.spring.spel;


import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * spel测试
 *
 * @author lzy
 * @version v1.0
 */
public class SPELTest {
    private ExpressionParser parser = new SpelExpressionParser();
    private SimpleSPELBean simpleSpelBean = new SimpleSPELBean();

    /**
     * 测试字符表达式
     */
    @Test
    public void testLiteralExpressions() {
        assertThat(parser.parseExpression("'Hello World'").getValue(String.class))
                .isEqualTo("Hello World");
        //相当于调用"Hello World".getBytes()
        assertThat((byte[]) parser.parseExpression("'Hello World'.bytes").getValue())
                .isEqualTo("Hello World".getBytes());

        assertThat(parser.parseExpression("'Hello World'.bytes.length").getValue())
                .isEqualTo("Hello World".getBytes().length);

        //正则匹配
        assertThat(parser.parseExpression("'5.00' matches '^-?\\d+(\\.\\d{2})?$'").getValue())
                .isEqualTo(true);

    }

    /**
     * 测试上下文
     */
    @Test
    public void testEvaluationContext() {
        //可以添加表达式的上下文,相当于调用Arrays.asList(1, 2).size(),可以看作是size()方法委托给context的对象来执行
        List list = Arrays.asList(1, 2);
        EvaluationContext context = new StandardEvaluationContext(list);
        assertThat(parser.parseExpression("size()").getValue(context))
                .isEqualTo(list.size());

        //也可以直接添加要委托执行的对象,相当于Arrays.asList(1,2).size()
        assertThat(parser.parseExpression("size()").getValue(list))
                .isEqualTo(list.size());
    }


    /**
     * 测试list,
     */
    @Test
    public void testList() {
        //使用{}表示list
        assertThat(parser.parseExpression("{1,2,3,4}").getValue())
                .asList().containsExactly(1, 2, 3, 4);
        // 嵌套数组
        assertThat(parser.parseExpression("{{'1','2'},{'3','4'}}").getValue())
                .asList().containsExactly(Arrays.asList("1", "2"), Arrays.asList("3", "4"));
        //使用[]获取值
        assertThat(parser.parseExpression("{1,2,3,4}[0]").getValue()).isEqualTo(1);
        // 从上下文获取值
        simpleSpelBean.setList(Arrays.asList(1, 2));
        // SimpleSPELBean有getList方法
        assertThat(parser.parseExpression("list[0]").getValue(simpleSpelBean)).isEqualTo(1);
    }

    /**
     * 测试map
     */
    @Test
    public void testMap() {
        //使用{key:value}表示map,使用[key]获取值
        assertThat(parser.parseExpression("{'key':'value'}['key']").getValue())
                .isEqualTo("value");
        // 从上下文获取值
        simpleSpelBean.setMap(parser.parseExpression("{'key':'value'}").getValue(Map.class));
        // SimpleSPELBean有getMap方法
        assertThat(parser.parseExpression("map['key']").getValue(simpleSpelBean))
                .isEqualTo("value");
    }

    /**
     * 测试调用方法
     */
    @Test
    public void testMethod() {
        // 相当于调用了simpleSpelBean#method
        assertThat(parser.parseExpression("method('hello world')").getValue(simpleSpelBean))
                .isEqualTo("method:hello world");

        //使用等号相当于调用了属性的set方法,这里调用了Demo#setList()
        assertThat(parser.parseExpression("list = {1}").getValue(simpleSpelBean))
                .asList().containsExactly(1);
        assertThat(simpleSpelBean.getList()).asList().containsExactly(1);
    }

    /**
     * 测试类
     */
    @Test
    public void testClass() {
        // T表示类,java.lang可以省略,其它需要全限定类名
        assertThat(parser.parseExpression("T(Integer)").getValue())
                .isEqualTo(Integer.class);
        assertThat(parser.parseExpression("'hello world' instanceof T(String)").getValue())
                .isEqualTo(true);
        // 枚举
        assertThat(parser.parseExpression("T(com.lzy.demo.spring.spel.SPELEnums).ENUM1").getValue())
                .isInstanceOf(SPELEnums.class);

        // 创建实例
        assertThat(parser.parseExpression("new com.lzy.demo.spring.spel.SimpleSPELBean()").getValue())
                .isInstanceOf(SimpleSPELBean.class);

    }

    /**
     * 测试数学运算符,lt(<),gt(>),le(<=),ge(>=),eq(==),ne(!=),add(+),minus(-),mul(*),div(/),mod(%)
     */
    @Test
    public void testRelation() {
        assertThat(parser.parseExpression("3 == 3").getValue()).isEqualTo(true);
        assertThat(parser.parseExpression("2.3 < 3").getValue()).isEqualTo(true);
    }

    /**
     * 测试逻辑运算符,and(&&),or(||),not(!)
     */
    @Test
    public void testLogical() {
        assertThat(parser.parseExpression("true and true").getValue()).isEqualTo(true);
        assertThat(parser.parseExpression("true or false").getValue()).isEqualTo(true);
        assertThat(parser.parseExpression("!false").getValue()).isEqualTo(true);
    }


    /**
     * 测试三目运算符
     */
    @Test
    public void testTernaryOperator() {
        // 相当于 false?"trueExp":falseExp"
        assertThat(parser.parseExpression("false ? 'trueExp' : 'falseExp'").getValue()).isEqualTo("falseExp");
        // 相当于simpleSpelBean.getList()==null?name:"Unknown"
        assertThat(parser.parseExpression("list?:'Unknown'").getValue(simpleSpelBean)).isEqualTo("Unknown");
        // 相当于simpleSpelBean.getInnerClass()==null?null?simpleSpelBean.getInnerClass().getProperty()
        assertThat(parser.parseExpression("innerClass?.property").getValue(simpleSpelBean)).isEqualTo("value");
    }

    /**
     * 测试参数
     */
    @Test
    public void testVariables() {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("variable1", "variable1 value");
        context.setVariable("variable2", "variable2 value");
        // 使用#variable,获取上下文的参数值
        assertThat(parser.parseExpression("#variable1").getValue(context))
                .isEqualTo("variable1 value");
        assertThat(parser.parseExpression("#variable2").getValue(context))
                .isEqualTo("variable2 value");
    }

    /**
     * 测试#this和#root
     */
    @Test
    public void testThisAndRoot() {
        List list = Arrays.asList(1, 2, 3, 4, 5);
        StandardEvaluationContext context = new StandardEvaluationContext(list);
        //#root获得委托对象
        assertThat(parser.parseExpression("#root").getValue(context)).isEqualTo(list);

        //#this获得当前正在计算的值,在这里,#this获得列表的所有值,然后分别执行判断操作,最后输出列表中大于3的值
        assertThat(parser.parseExpression("#root.?[#this>3]").getValue(context))
                .asList().containsExactly(4, 5);
    }

}
