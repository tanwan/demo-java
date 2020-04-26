/*
 * Created by lzy on 2019-03-29 14:25.
 */
package com.lzy.demo.aspectj.aspect;

import com.lzy.demo.aspectj.annotation.SimpleAnnotation;
import com.lzy.demo.aspectj.bean.SimpleAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * 使用注解的切面
 *
 * @author lzy
 * @version v1.0
 */
@Aspect
public class AnnotationAspect {


    /**
     * Execution pointcut.
     */
    @Pointcut("execution(void execution())")
    public void executionPointcut() {
    }


    /**
     * 等价于@Around("execution(void execution())")
     *
     * @param pjp the pjp
     * @throws Throwable the throwable
     */
    @Around("executionPointcut()")
    public void execution(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("execution before: " + pjp.getSignature());
        pjp.proceed();
        System.out.println("execution after");
    }

    /**
     * 参数需要注解(如果在编译时织入,则注解的Retention不能是SOURCE,如果是运行时织入,那么注解的Retention一定是RUNTIME)
     * 方法需要有@SimpleAnnotation注解,第一个参数也需要有@SimpleAnnotation注解
     *
     * @param jp the jp
     */
    @Before("execution(void parameterAnnotation(@com.lzy.demo.aspectj.annotation.SimpleAnnotation (*),*))")
    public void parameterAnnotation(JoinPoint jp) {
        System.out.println("before: " + jp.getSignature());
    }

    /**
     * 类型需要注解
     * 返回值的类需要有@SimpleAnnotation注解,第一个参数的类也需要有@SimpleAnnotation注解
     *
     * @param jp the jp
     */
    @After("execution((@com.lzy.demo.aspectj.annotation.SimpleAnnotation *) typeAnnotation((@com.lzy.demo.aspectj.annotation.SimpleAnnotation *),*))")
    public void typeAnnotation(JoinPoint jp) {
        System.out.println("after: " + jp.getSignature());
    }

    /**
     * 连接点为void atWithin(),并且该连接点所属的类需要有@SimpleAnnotation注解
     *
     * @param pjp the pjp
     * @throws Throwable the throwable
     */
    @Around("execution(void atWithin()) && @within(com.lzy.demo.aspectj.annotation.SimpleAnnotation)")
    public void atWithin(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("atWithin before: " + pjp.getSignature());
        pjp.proceed();
        System.out.println("atWithin after");
    }

    /**
     * 接入点为void thiz(),并且接入点的this对象属于SimpleAspect
     *
     * @param pjp the pjp
     * @throws Throwable the throwable
     */
    @Around("execution(void thiz())&&this(com.lzy.demo.aspectj.bean.SimpleAspect)")
    public void thiz(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("this before: " + pjp.getSignature());
        pjp.proceed();
        System.out.println("this after");
    }

    /**
     * 接入点为void thisArgs(),并且接入点的this对象属于SimpleAspect,获取this的参数
     *
     * @param bean the bean
     * @param pjp  the pjp
     */
    @Before("execution(void thisArgs())&&this(com.lzy.demo.aspectj.bean.SimpleAspect)&&this(bean)")
    public void thisArgs(SimpleAspect bean, JoinPoint pjp) {
        System.out.println("this before: " + pjp.getSignature());
        System.out.println("bean:" + bean.getClass());
    }


    /**
     * 接入点为void target(),并且接入点的target对象属于SimpleAspect
     * aspectj可以使用call,spring aop不支持
     *
     * @param pjp the pjp
     * @throws Throwable the throwable
     */
    @Around("call(void target())&&target(com.lzy.demo.aspectj.bean.SimpleAspect)")
    public void target(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("target before: " + pjp.getSignature());
        pjp.proceed();
        System.out.println("target after");
    }

    /**
     * 接入点为void atTarget(),并且接入点的target对象需要有@SimpleAnnotation注解
     * aspectj可以使用call,spring aop不支持
     *
     * @param pjp the pjp
     * @throws Throwable the throwable
     */
    @Around("call(void atTarget())&&@target(com.lzy.demo.aspectj.annotation.SimpleAnnotation)")
    public void atTarget(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("atTarget before: " + pjp.getSignature());
        pjp.proceed();
        System.out.println("atTarget after");
    }


    /**
     * 使用args指定参数并获取参数
     *
     * @param arg1   the arg 1
     * @param thiz   the thiz
     * @param target the target
     * @param pjp    the pjp
     * @throws Throwable the throwable
     */
    @Around("execution(void args(..)) && args(arg1) && this(thiz) && target(target)")
    public void args(int arg1, Object thiz, Object target, ProceedingJoinPoint pjp) throws Throwable {
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
    @Around("execution(void atArgs(..)) && @args(com.lzy.demo.aspectj.annotation.SimpleAnnotation,*,*)")
    public void atArgs(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("atArgs before " + pjp.getSignature());
        pjp.proceed();
        System.out.println("atArgs after");
    }

    /**
     * 方法需要有@SimpleAnnotation注解(如果类上有@SimpleAnnotation注解,则这个类的clinit方法也会被增强)
     * 同execution(@com.lzy.demo.aspectj.annotation.SimpleAnnotation * *())一样
     * 如果不需要获取注解的值,则使用@annotation(com.lzy.demo.aspectj.annotation.SimpleAnnotation)
     *
     * @param annotation the annotation
     * @param pjp        the pjp
     * @throws Throwable the throwable
     */
    @Around(value = "@annotation(annotation)")
    public void atAnnotation(SimpleAnnotation annotation, ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("annotation:" + annotation.toString() + " atAnnotation before " + pjp.getSignature());
        pjp.proceed();
        System.out.println("atAnnotation after");
    }


    /**
     * 捕获异常
     * 异常需要捕获
     * spring aop不支持
     *
     * @param e the e
     */
    @Before("handler(java.lang.Exception+)&&args(e)")
    public void handler(Exception e) {
        System.out.println("handler:" + e.getMessage());
    }


    /**
     * 通知的顺序
     */
    @Pointcut("execution(void *..AspectSimpleBean1.order())")
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
