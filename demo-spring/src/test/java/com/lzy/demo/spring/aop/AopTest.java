package com.lzy.demo.spring.aop;

import com.lzy.demo.spring.aop.annotation.Param;
import com.lzy.demo.spring.aop.annotation.SimpleAnnotation;
import com.lzy.demo.spring.aop.aspect.AnnotationAspect;
import com.lzy.demo.spring.aop.aspect.ClauseAspect;
import com.lzy.demo.spring.aop.bean.SimpleBeanInterface;
import com.lzy.demo.spring.aop.bean.SimpleBean1;
import com.lzy.demo.spring.aop.bean.SimpleBean2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * spring aop测试,基于动态代码
 *
 * @author lzy
 * @version v1.0
 */
@SpringJUnitConfig({SimpleBean1.class, SimpleBean2.class, AnnotationAspect.class, ClauseAspect.class})
@EnableAspectJAutoProxy
public class AopTest {

    @Autowired
    private SimpleBeanInterface simpleBean1;

    @Autowired
    private SimpleBeanInterface simpleBean2;

    /**
     * 测试execution
     *
     * @see AnnotationAspect#execution(ProceedingJoinPoint)
     */
    @Test
    public void testExecution() {
        simpleBean1.execution();
        simpleBean2.execution();
    }


    /**
     * 测试参数需要注解
     *
     * @see AnnotationAspect#parameterAnnotation(JoinPoint)
     */
    @Test
    public void testParameterAnnotation() {
        simpleBean1.parameterAnnotation(1, 2);
        // SimpleBean2的第一个参数没有注解,所以没有aop
        simpleBean2.parameterAnnotation(1, 2);
    }

    /**
     * 测试类型需要注解
     *
     * @see AnnotationAspect#typeAnnotation(JoinPoint)
     */
    @Test
    public void typeAnnotation() {
        simpleBean1.typeAnnotation(new Param(), 2);
        simpleBean2.typeAnnotation(new Param(), 2);
    }


    /**
     * 测试within
     *
     * @see AnnotationAspect#within(ProceedingJoinPoint)
     */
    @Test
    public void testWithin() {
        simpleBean1.within();
        // SimpleBean2不符合within的条件,所以没有aop
        simpleBean2.within();
    }

    /**
     * 测试@within
     *
     * @see AnnotationAspect#atWithin(ProceedingJoinPoint)
     */
    @Test
    public void testAtWithin() {
        // SimpleBean1不符合@within的条件,所以没有aop
        simpleBean1.atWithin();
        simpleBean2.atWithin();
    }

    /**
     * 测试this
     *
     * @see AnnotationAspect#thiz(ProceedingJoinPoint)
     */
    @Test
    public void testThis() {
        simpleBean1.thiz();
        simpleBean2.thiz();
    }

    /**
     * 测试this并获取参数
     *
     * @see AnnotationAspect#thisArgs(JoinPoint, SimpleBeanInterface)
     */
    @Test
    public void testThisArgs() {
        simpleBean1.thisArgs();
        simpleBean2.thisArgs();
    }

    /**
     * 测试target
     *
     * @see AnnotationAspect#target(ProceedingJoinPoint) (ProceedingJoinPoint)
     */
    @Test
    public void testTarget() {
        simpleBean1.target();
        // SimpleBean2不符合target的条件,所以没有aop
        simpleBean2.target();
    }

    /**
     * 测试target并获取参数
     *
     * @see AnnotationAspect#targetArgs(JoinPoint, SimpleBeanInterface)
     */
    @Test
    public void testTargetArgs() {
        simpleBean1.targetArgs();
        simpleBean2.targetArgs();
    }

    /**
     * 测试@target
     *
     * @see AnnotationAspect#atTarget(ProceedingJoinPoint)
     */
    @Test
    public void testAtTarget() {
        // SimpleBean1不符合@target的条件,所以没有aop
        simpleBean1.atTarget();
        simpleBean2.atTarget();
    }

    /**
     * 测试args
     *
     * @see AnnotationAspect#args(ProceedingJoinPoint, int, Object, Object)
     */
    @Test
    public void testArgs() {
        simpleBean1.args(1);
        simpleBean2.args(1);
    }


    /**
     * 测试@args
     *
     * @see AnnotationAspect#atArgs(ProceedingJoinPoint)
     */
    @Test
    public void testAtArgs() {
        simpleBean1.atArgs(new Param(), 1, 2);
        simpleBean2.atArgs(new Param(), 1, 2);
    }

    /**
     * 测试@annotation
     *
     * @see AnnotationAspect#atAnnotation(ProceedingJoinPoint, SimpleAnnotation)
     */
    @Test
    public void testAtAnnotation() {
        simpleBean1.atAnnotation();
        // SimpleBean2不符合annotation的条件,所以没有aop
        simpleBean2.atAnnotation();
    }


    /**
     * 测试aop顺序
     *
     * @see AnnotationAspect#aroundOrder(ProceedingJoinPoint)
     * @see AnnotationAspect#beforeOrder()
     * @see AnnotationAspect#afterOrder()
     */
    @Test
    public void testOrder() {
        simpleBean1.order();
    }

    /**
     * 测试实例化模型
     *
     * @see ClauseAspect#execution(ProceedingJoinPoint)
     */
    @Test
    public void testClause() {
        simpleBean1.clause();
        simpleBean2.clause();
    }
}
