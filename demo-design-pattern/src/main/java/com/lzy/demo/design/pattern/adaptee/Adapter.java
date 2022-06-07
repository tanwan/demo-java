package com.lzy.demo.design.pattern.adaptee;

/**
 * 适配器
 * 把一个接口变换成客户端所期待的另一种接口,从而使原本因接口不匹配而无法在一起工作的两个类能够在一起工作
 *
 * @author LZY
 * @version v1.0
 */
public class Adapter implements Target {
    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    /**
     * 目标接口方法
     *
     * @param number the number
     */
    @Override
    public void method(String number) {
        adaptee.method(Integer.valueOf(number));
    }
}
