package com.lzy.demo.spring.aop.bean;


import com.lzy.demo.spring.aop.annotation.Param;
import com.lzy.demo.spring.aop.annotation.SimpleAnnotation;
import org.springframework.stereotype.Component;

@Component
@SimpleAnnotation
public class SimpleBean2 implements SimpleBeanInterface {

    @Override
    public void execution() {
        System.out.println("SimpleAspectBean2 execution");
    }

    @Override
    public void parameterAnnotation(int param1, int param2) {
        System.out.println("SimpleAspectBean2 parameterAnnotation");
    }

    @Override
    public Param typeAnnotation(Param param1, int param2) {
        System.out.println("SimpleAspectBean2 typeAnnotation");
        return param1;
    }

    @Override
    public void within() {
        System.out.println("SimpleAspectBean2 within");
    }

    @Override
    public void atWithin() {
        System.out.println("SimpleAspectBean2 atWithin");
    }

    @Override
    public void thiz() {
        System.out.println("SimpleAspectBean2 thiz");
    }

    @Override
    public void thisArgs() {
        System.out.println("SimpleAspectBean2 thisArgs");
    }

    @Override
    public void target() {
        System.out.println("SimpleAspectBean2 target");
    }

    @Override
    public void atTarget() {
        System.out.println("SimpleAspectBean2 atTarget");
    }

    @Override
    public void targetArgs() {
        System.out.println("SimpleAspectBean2 targetArgs");
    }

    @Override
    public void args(int i) {
        System.out.println("SimpleAspectBean2 args:" + i);
    }

    @Override
    public void atArgs(Param param1, int param2, int param3) {
        System.out.println("SimpleAspectBean2 atArgs");
    }

    @Override
    public void atAnnotation() {
        System.out.println("SimpleAspectBean2 atAnnotation");
    }

    @Override
    public void order() {
        System.out.println("SimpleAspectBean2 order");
    }

    @Override
    public void clause() {
        System.out.println("SimpleAspectBean2 clause");
    }
}
