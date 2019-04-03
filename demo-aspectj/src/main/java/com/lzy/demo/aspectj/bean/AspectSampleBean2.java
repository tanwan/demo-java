/*
 * Created by lzy on 2019-03-29 00:05.
 */
package com.lzy.demo.aspectj.bean;

import com.lzy.demo.aspectj.annotation.Param;
import com.lzy.demo.aspectj.annotation.SampleAnnotation;

/**
 * @author lzy
 * @version v1.0
 */
@SampleAnnotation
public class AspectSampleBean2 implements AspectSample {


    @Override
    public void call() {
        System.out.println("AspectSampleBean2 call");
    }

    @Override
    public void execution() {
        System.out.println("AspectSampleBean2 execution");
    }

    @Override
    public void parameterAnnotation(int param1, int param2) {
        System.out.println("AspectSampleBean2 parameterAnnotation");
    }

    @Override
    public Param typeAnnotation(Param param1, int param2) {
        System.out.println("AspectSampleBean2 typeAnnotation");
        return param1;
    }

    @Override
    public void within() {
        System.out.println("AspectSampleBean2 within");
    }

    @Override
    public void atWithin() {
        System.out.println("AspectSampleBean2 atWithin");
    }

    @Override
    public void thiz() {
        System.out.println("AspectSampleBean2 thiz");
    }

    @Override
    public void thisArgs() {
        System.out.println("AspectSampleBean2 thisArgs");
    }

    @Override
    public void target() {
        System.out.println("AspectSampleBean2 target");
    }

    @Override
    public void atTarget() {
        System.out.println("AspectSampleBean2 atTarget");
    }

    @Override
    public void targetArgs() {
        System.out.println("AspectSampleBean2 targetArgs");
    }

    @Override
    public void args(int i) {
        System.out.println("AspectSampleBean2 args:" + i);
    }

    @Override
    public void atArgs(Param param1, int param2, int param3) {
        System.out.println("AspectSampleBean2 atArgs");
    }

    @Override
    public void atAnnotation() {
        System.out.println("AspectSampleBean2 atAnnotation");
    }

    @Override
    public void handler() throws ArithmeticException {
        try {
            int i = 8 / 0;
        } catch (ArithmeticException e) {
            System.out.println("AspectSampleBean2 handler");
        }
    }

    @Override
    public void order() {
        System.out.println("AspectSampleBean2 order");
    }

    @Override
    public void clause() {
        System.out.println("AspectSampleBean2 clause");
    }
}
