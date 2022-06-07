package com.lzy.demo.spring.aop.bean;


import com.lzy.demo.spring.aop.annotation.Param;
import com.lzy.demo.spring.aop.annotation.SimpleAnnotation;
import org.springframework.stereotype.Component;

/**
 * The type SimpleAspectBean1.
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class SimpleBean1 implements SimpleBeanInterface {

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
    public void order() {
        System.out.println("SimpleAspectBean1 order");
    }

    @Override
    public void clause() {
        System.out.println("SimpleAspectBean1 clause");
    }
}
