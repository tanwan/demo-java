package com.lzy.demo.spring.aop.aspect;

import com.lzy.demo.spring.aop.annotation.SimpleAnnotation;
import com.lzy.demo.spring.aop.bean.SimpleBeanInterface;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 使用注解的切面
 *
 * @author lzy
 * @version v1.0
 */
@Aspect
public class AnnotationAspect {

    /**
     * 定义切点
     */
    @Pointcut("execution(void execution())")
    public void executionPointcut() {
    }


    /**
     * 使用定义好的切点
     * 等价于@Around("execution(void execution())")
     *
     * @param pjp the pjp
     * @throws Throwable the throwable
     * @see AnnotationAspect#executionPointcut()
     */
    @Around("executionPointcut()")
    public void execution(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("execution before: " + pjp.getSignature());
        pjp.proceed();
        System.out.println("execution after");
    }

    /**
     * 参数需要注解(这边是动态代理,所以注解的Retention一定是RUNTIME)
     * 第一个参数需要有@SimpleAnnotation注解
     *
     * @param jp the jp
     */
    @Before("execution(void parameterAnnotation(@com.lzy.demo.spring.aop.annotation.SimpleAnnotation (*),*))")
    public void parameterAnnotation(JoinPoint jp) {
        System.out.println("before: " + jp.getSignature());
    }

    /**
     * 类型需要注解
     * 返回值的类需要有@SimpleAnnotation注解,第一个参数的类也需要有@SimpleAnnotation注解
     *
     * @param jp the jp
     */
    @After("execution((@com.lzy.demo.spring.aop.annotation.SimpleAnnotation *) typeAnnotation((@com.lzy.demo.spring.aop.annotation.SimpleAnnotation *),*))")
    public void typeAnnotation(JoinPoint jp) {
        System.out.println("after: " + jp.getSignature());
    }


    /**
     * 连接点为void within(),并且该连接点属于SimpleAspectBean1
     * within严格匹配类型的,不会理会继承关系,这边如果使用within(com.lzy.demo.spring.aop.bean.SimpleAspectBean),也不会匹配到SimpleAspectBean1和SimpleAspectBean2
     *
     * @param pjp the pjp
     * @throws Throwable the throwable
     */
    @Around("execution(void within()) && within(com.lzy.demo.spring.aop.bean.SimpleBean1)")
    public void within(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("with before: " + pjp.getSignature());
        pjp.proceed();
        System.out.println("with after");
    }

    /**
     * 连接点为void atWithin(),并且该连接点所属的类需要有@SimpleAnnotation注解
     *
     * @param pjp the pjp
     * @throws Throwable the throwable
     */
    @Around("execution(void atWithin()) && @within(com.lzy.demo.spring.aop.annotation.SimpleAnnotation)")
    public void atWithin(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("atWithin before: " + pjp.getSignature());
        pjp.proceed();
        System.out.println("atWithin after");
    }

    /**
     * 连接点为void thiz(),并且连接点的this对象属于SimpleAspect,也就是代理后的对象属于SimpleAspect
     *
     * @param pjp the pjp
     * @throws Throwable the throwable
     */
    @Around("execution(void thiz()) && this(com.lzy.demo.spring.aop.bean.SimpleBeanInterface)")
    public void thiz(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("this before: " + pjp.getSignature());
        pjp.proceed();
        System.out.println("this after");
    }


    /**
     * 连接点为void thisArgs(),并且连接点的this对象属于SimpleAspect,获取this的参数
     * JoinPoint需要是第一个参数
     *
     * @param bean the bean
     * @param pjp  the pjp
     */
    @Before("execution(void thisArgs()) && this(bean)")
    public void thisArgs(JoinPoint pjp, SimpleBeanInterface bean) {
        System.out.println("this before: " + pjp.getSignature());
        System.out.println("bean:" + bean);
    }


    /**
     * 连接点为void target(),并且连接点的target对象属于SimpleAspectBean1,也就是被代理的对象属于SimpleAspectBean1
     *
     * @param pjp the pjp
     * @throws Throwable the throwable
     */
    @Around("execution(void target()) && target(com.lzy.demo.spring.aop.bean.SimpleBean1)")
    public void target(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("target before: " + pjp.getSignature());
        pjp.proceed();
        System.out.println("target after");
    }

    /**
     * 连接点为void atTarget(),并且连接点的target对象需要有@SimpleAnnotation注解,也就是被代理的对象需要有@SimpleAnnotation注解
     *
     * @param pjp the pjp
     * @throws Throwable the throwable
     */
    @Around("execution(void atTarget()) && @target(com.lzy.demo.spring.aop.annotation.SimpleAnnotation)")
    public void atTarget(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("atTarget before: " + pjp.getSignature());
        pjp.proceed();
        System.out.println("atTarget after");
    }


    /**
     * 连接点为void targetArgs(),并且连接点的target对象属于SimpleAspectBean1,也就是被代理的对象属于SimpleAspectBean1,获取target对象
     * JoinPoint需要是第一个参数
     *
     * @param target the target
     * @param pjp    the pjp
     */
    @Before("execution(void targetArgs()) && target(target)")
    public void targetArgs(JoinPoint pjp, SimpleBeanInterface target) {
        System.out.println("target before: " + pjp.getSignature());
        System.out.println("target:" + target);
    }

    /**
     * 使用args指定参数并获取参数
     * ProceedingJoinPoint需要是第一个参数
     * this为代理后的对象
     * target为代理前的对象
     *
     * @param arg1   the arg 1
     * @param thiz   the thiz
     * @param target the target
     * @param pjp    the pjp
     * @throws Throwable the throwable
     */
    @Around("execution(void args(..)) && args(arg1) && this(thiz) && target(target)")
    public void args(ProceedingJoinPoint pjp, int arg1, Object thiz, Object target) throws Throwable {
        System.out.println("arg1:" + arg1 + "args before " + pjp.getSignature());
        System.out.println("this:" + thiz.getClass());
        System.out.println("target:" + target.getClass());
        pjp.proceed();
        System.out.println("args after");
    }


    /**
     * 使用@args
     *
     * @param pjp the pjp
     * @throws Throwable the throwable
     */
    @Around("execution(void atArgs(..)) && @args(com.lzy.demo.spring.aop.annotation.SimpleAnnotation,*,*)")
    public void atArgs(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("atArgs before " + pjp.getSignature());
        pjp.proceed();
        System.out.println("atArgs after");
    }

    /**
     * 方法需要有@SimpleAnnotation注解(如果类上有@SimpleAnnotation注解,则这个类的clinit方法也会被增强)
     * 同execution(@com.lzy.demo.spring.aop.annotation.SimpleAnnotation * *())一样
     * 如果不需要获取注解的值,则使用@annotation(com.lzy.demo.spring.aop.annotation.SimpleAnnotation)
     * 这边在方法的参数上有了SimpleAnnotation,所以只需要使用@annotation(annotation),annotation为参数的名称
     *
     * @param annotation the annotation
     * @param pjp        the pjp
     * @throws Throwable the throwable
     */
    @Around("@annotation(annotation)")
    public void atAnnotation(ProceedingJoinPoint pjp, SimpleAnnotation annotation) throws Throwable {
        System.out.println("annotation:" + annotation.toString() + " atAnnotation before " + pjp.getSignature());
        pjp.proceed();
        System.out.println("atAnnotation after");
    }

    /**
     * 通知的顺序
     */
    @Pointcut("execution(void *..SimpleAspectBean1.order())")
    public void orderPointcut() {
    }

    /**
     * Around order.
     *
     * @param pjp the pjp
     * @throws Throwable the throwable
     */
    @Around("orderPointcut()")
    public void aroundOrder(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("around before proceed");
        pjp.proceed();
        System.out.println("around after proceed");
    }

    /**
     * Before order.
     */
    @Before("orderPointcut()")
    public void beforeOrder() {
        System.out.println("before");
    }


    /**
     * After order.
     */
    @After("orderPointcut()")
    public void afterOrder() {
        System.out.println("after");
    }
}
