/*
 * Created by lzy on 2019-03-29 00:06.
 */
package com.lzy.demo.aspectj.bean;

import com.lzy.demo.aspectj.annotation.SimpleAnnotation;
import com.lzy.demo.aspectj.annotation.Param;

/**
 * The type SimpleAspectBean1.
 *
 * @author lzy
 * @version v1.0
 */
public class SimpleAspectBean1 implements SimpleAspect {


    @Override
    public void call() {
        System.out.println("SimpleAspectBean1 call");
    }

    @Override
    public void execution() {
        System.out.println("SimpleAspectBean1 execution");
    }

    @Override
    public void parameterAnnotation(@SimpleAnnotation int param1, int param2) {
        System.out.println("SimpleAspectBean1 parameterAnnotation");
    }

    @Override
    public Param typeAnnotation(Param param1, int param2) {
        System.out.println("SimpleAspectBean1 typeAnnotation");
        return param1;
    }

    @Override
    public void within() {
        System.out.println("SimpleAspectBean1 within");
    }

    @Override
    public void atWithin() {
        System.out.println("SimpleAspectBean1 atWithin");
    }

    @Override
    public void thiz() {
        System.out.println("SimpleAspectBean1 thiz");
    }

    @Override
    public void thisArgs() {
        System.out.println("SimpleAspectBean1 thisArgs");
    }

    @Override
    public void target() {
        System.out.println("SimpleAspectBean1 target");
    }

    @Override
    public void atTarget() {
        System.out.println("SimpleAspectBean1 atTarget");
    }

    @Override
    public void targetArgs() {
        System.out.println("SimpleAspectBean1 targetArgs");
    }

    @Override
    public void args(int i) {
        System.out.println("SimpleAspectBean1 args:" + i);
    }

    @Override
    public void atArgs(Param param1, int param2, int param3) {
        System.out.println("SimpleAspectBean1 atArgs");
    }

    @Override
    @SimpleAnnotation
    public void atAnnotation() {
        System.out.println("SimpleAspectBean1 atAnnotation");
    }

    @Override
    public void handler() {
        try {
            int i = 8 / 0;
        } catch (ArithmeticException e) {
            System.out.println("SimpleAspectBean1 handler");
        }
    }

    @Override
    public void order() {
        try {
            System.out.println("SimpleAspectBean1 order");
            int i = 8 / 0;
        } catch (ArithmeticException e) {
            System.out.println("SimpleAspectBean1 order catch");
        }
    }

    @Override
    public void clause() {
        System.out.println("SimpleAspectBean1 clause");
    }
}
