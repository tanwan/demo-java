package com.lzy.demo.base.recursive;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RecursiveTest {
    private long startTime;

    /**
     * 测试阶乘,递归
     */
    @Test
    public void testFactorial() {
        for (int i = 1; i < 20; i++) {
            System.out.println(factorial(i));
        }
    }

    /**
     * 测试阶乘,尾递归
     */
    @Test
    public void testFactorialTailRecursive() {
        for (int i = 1; i < 20; i++) {
            System.out.println(factorial(i, 1));
        }
    }


    /**
     * 测试斐波拉契,递归
     */
    @Test
    public void testFiboracci() {
        for (int i = 1; i < 40; i++) {
            System.out.println(fiboracci(i));
        }
    }

    /**
     * 测试斐波拉契,尾递归
     */
    @Test
    public void testFiboracciTailRecursive() {
        for (int i = 1; i < 40; i++) {
            System.out.println(fiboracci(i, 1, 1));
        }
    }

    private long factorial(long n) {
        //递归出口条件,能够简单计算出结果就可以直接返回,比如1!=1 0!=1
        if (n <= 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    /**
     * 阶乘,尾递归
     * 0 1 2 3  4  5
     * 1 1 2 6 24 120
     *
     * @param n      the n
     * @param result 结果
     * @return the int
     */
    private long factorial(long n, long result) {
        if (n <= 1) {
            //当n<=1的时候就可以得到结果
            //如果n传入的是1,那么result就必须要传1
            return result;
        } else {
            //尾递归其实是把上一次的计算结果传递给下一次
            return factorial(n - 1, n * result);
        }
    }

    private int fiboracci(int n) {
        //递归出口条件,能够简单计算出结果就可以直接返回,比如fiboracci(1)=1,fiboracci(2)=1
        if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 1;
        } else {
            return fiboracci(n - 1) + fiboracci(n - 2);
        }
    }

    /**
     * 斐波拉契,尾递归
     * 斐波拉契没有第0项,添加第0项为了方便理解
     * 0 1 2 3 4 5 6 7
     * 0 1 1 2 3 5 8 13
     *
     * @param n          the n
     * @param result     the 结果
     * @param nextResult the 下一个的结果
     * @return the int
     */
    private int fiboracci(int n, int result, int nextResult) {
        //尾递归就是把上次的结果带入到了下一次的计算中,斐波拉契数列是需要前两次的计算结果,如果这里需要传入两个结果
        if (n <= 0) {
            //如果返回的是result
            //如果n<=0,那么result必须等于0,nextResult就是下一个值,也就是1
            //如果n<=1,那么result必须等于1,nextResult就是下一个值,也就是1
            //如果n<=2,那么result必须等于1,nextResult就是下一个值,也就是2
            return result;

            //如果返回的是nextResult
            //当n<=0时的结果,因为result是他的前一个值,当n=0时,nextResult=0,而result并没有值
            //因此如果要返回nextResult时,n最少也要n<=1
            //如果n<=2,那么nextResult必须等于1,result就是前一个值,也就是1
            //return nextResult;
        } else {
            return fiboracci(n - 1, nextResult, result + nextResult);
        }
    }

    @BeforeEach
    public void start() {
        startTime = System.currentTimeMillis();
    }

    @AfterEach
    public void end() {
        System.out.println("spend time:" + (System.currentTimeMillis() - startTime));
    }
}
