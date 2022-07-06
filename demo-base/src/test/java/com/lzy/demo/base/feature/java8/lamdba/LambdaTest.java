package com.lzy.demo.base.feature.java8.lamdba;

import com.lzy.demo.base.feature.java8.lamdba.bean.LambdaInterface;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class LambdaTest {

    /**
     * 测试lambda表达式
     */
    @Test
    public void testLambda() {
        List<String> list = new ArrayList<>();
        LambdaInterface<String> lambdaInterface = x -> {
            //lambda表达式内的外部变量为final
            list.add(x);
            return x;
        };
        lambdaInterface.functionalInterface("hello world");
        System.out.println(list.toString());
    }

    /**
     * 测试默认方法
     */
    @Test
    public void testDefaultMethod() {
        LambdaInterface<Integer> lambdaInterface = x -> x + 2;
        lambdaInterface.defaultMethod();
    }

    /**
     * 测试重载方法
     */
    @Test
    public void testOverload() {
        //这里虽然有两个overload方法,但是lambda会使用更加具体的类型
        //IntBinaryOperator 继承了 BinaryOperator
        overload((x, y) -> x + y);
    }


    /**
     * 重载方法
     *
     * @param lambda the lambda
     */
    private void overload(BinaryOperator<Integer> lambda) {
        System.out.println("BinaryOperator");
    }

    /**
     * 重载方法
     *
     * @param lambda the lambda
     */
    private void overload(IntBinaryOperator lambda) {
        System.out.println("IntBinaryOperator");
    }

    /**
     * 继承BinaryOperator
     */
    interface IntBinaryOperator extends BinaryOperator<Integer> {

    }
}
