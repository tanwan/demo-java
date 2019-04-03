/*
 * Created by lzy on 2019-03-29 00:06.
 */
package com.lzy.demo.aspectj.bean;

import com.lzy.demo.aspectj.annotation.SampleAnnotation;
import com.lzy.demo.aspectj.annotation.Param;

/**
 * The type AspectSampleBean1.
 *
 * @author lzy
 * @version v1.0
 */
public class AspectSampleBean1 implements AspectSample {


    @Override
    public void call() {
        System.out.println("AspectSampleBean1 call");
    }

    @Override
    public void execution() {
        System.out.println("AspectSampleBean1 execution");
    }

    @Override
    public void parameterAnnotation(@SampleAnnotation int param1, int param2) {
        System.out.println("AspectSampleBean1 parameterAnnotation");
    }

    @Override
    public Param typeAnnotation(Param param1, int param2) {
        System.out.println("AspectSampleBean1 typeAnnotation");
        return param1;
    }

    @Override
    public void within() {
        System.out.println("AspectSampleBean1 within");
    }

    @Override
    public void atWithin() {
        System.out.println("AspectSampleBean1 atWithin");
    }

    @Override
    public void thiz() {
        System.out.println("AspectSampleBean1 thiz");
    }

    @Override
    public void thisArgs() {
        System.out.println("AspectSampleBean1 thisArgs");
    }

    @Override
    public void target() {
        System.out.println("AspectSampleBean1 target");
    }

    @Override
    public void atTarget() {
        System.out.println("AspectSampleBean1 atTarget");
    }

    @Override
    public void targetArgs() {
        System.out.println("AspectSampleBean1 targetArgs");
    }

    @Override
    public void args(int i) {
        System.out.println("AspectSampleBean1 args:" + i);
    }

    @Override
    public void atArgs(Param param1, int param2, int param3) {
        System.out.println("AspectSampleBean1 atArgs");
    }

    @Override
    @SampleAnnotation
    public void atAnnotation() {
        System.out.println("AspectSampleBean1 atAnnotation");
    }

    @Override
    public void handler() {
        try {
            int i = 8 / 0;
        } catch (ArithmeticException e) {
            System.out.println("AspectSampleBean1 handler");
        }
    }

    @Override
    public void order() {
        try {
            System.out.println("AspectSampleBean1 order");
            int i = 8 / 0;
        } catch (ArithmeticException e) {
            System.out.println("AspectSampleBean1 order catch");
        }
    }

    @Override
    public void clause() {
        System.out.println("AspectSampleBean1 clause");
    }
}
