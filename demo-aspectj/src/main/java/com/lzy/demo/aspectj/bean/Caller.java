/*
 * Created by lzy on 2019-03-29 00:08.
 */
package com.lzy.demo.aspectj.bean;

import com.lzy.demo.aspectj.annotation.Param;

import java.util.List;

/**
 * The type Caller.
 *
 * @author lzy
 * @version v1.0
 */
public class Caller {

    private List<AspectSample> aspectSamples;

    /**
     * Instantiates a new Caller.
     *
     * @param aspectSamples the aspect samples
     */
    public Caller(List<AspectSample> aspectSamples) {
        this.aspectSamples = aspectSamples;
    }

    /**
     * 调用call
     */
    public void call() {
        System.out.println("Caller call");
        // 不能使用方法引用
        aspectSamples.forEach(t -> t.call());
    }

    /**
     * 调用call
     */
    public void execution() {
        System.out.println("Caller execution");
        // 不能使用方法引用
        aspectSamples.forEach(t -> t.execution());
    }

    /**
     * 调用parameterAnnotation
     */
    public void parameterAnnotation() {
        System.out.println("Caller parameterAnnotation");
        // 不能使用方法引用
        aspectSamples.forEach(t -> t.parameterAnnotation(1, 2));
    }

    /**
     * 调用typeAnnotation
     */
    public void typeAnnotation() {
        System.out.println("Caller parameterAnnotation");
        // 不能使用方法引用
        aspectSamples.forEach(t -> t.typeAnnotation(new Param(), 2));
    }


    /**
     * 调用within
     */
    public void within() {
        System.out.println("Caller within");
        aspectSamples.forEach(t -> t.within());
    }


    /**
     * 调用atWithin
     */
    public void atWithin() {
        System.out.println("Caller atWithin");
        aspectSamples.forEach(t -> t.atWithin());
    }

    /**
     * 调用thiz
     */
    public void thiz() {
        System.out.println("Caller thiz");
        aspectSamples.forEach(t -> t.thiz());
    }

    /**
     * 调用thisArgs
     */
    public void thisArgs() {
        System.out.println("Caller thisArgs");
        aspectSamples.forEach(t -> t.thisArgs());
    }


    /**
     * 调用target
     */
    public void target() {
        System.out.println("Caller target");
        aspectSamples.forEach(t -> t.target());
    }

    /**
     * 调用target
     */
    public void atTarget() {
        System.out.println("Caller atTarget");
        aspectSamples.forEach(t -> t.atTarget());
    }

    /**
     * 调用target
     */
    public void targetArgs() {
        System.out.println("Caller targetArgs");
        aspectSamples.forEach(t -> t.targetArgs());
    }

    /**
     * 调用args
     */
    public void args() {
        System.out.println("Caller args");
        aspectSamples.forEach(t -> t.args(1));
    }


    /**
     * 调用args
     */
    public void atArgs() {
        System.out.println("Caller args");
        aspectSamples.forEach(t -> t.atArgs(new Param(), 1, 2));
    }

    /**
     * 调用atAnnotation
     */
    public void atAnnotation() {
        System.out.println("Caller atAnnotation");
        aspectSamples.forEach(t -> t.atAnnotation());
    }


    /**
     * 调用handler
     */
    public void handler() {
        System.out.println("Caller handler");
        aspectSamples.forEach(t -> t.handler());
    }

    /**
     * 调用order
     */
    public void order() {
        System.out.println("Caller order");
        aspectSamples.forEach(t -> t.order());
    }

    /**
     * 调用clause
     */
    public void clause() {
        System.out.println("Caller clause");
        aspectSamples.forEach(t -> t.clause());
    }
}
