package com.lzy.demo.base.bean;

import java.io.Serializable;

public class GenericsClass<T> implements GenericsInterface<T> {
    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    /**
     * 泛型方法 在修饰词后面指定泛型类型
     *
     * @param <S> the type parameter
     * @param s   the s
     */
    public <S> void genericFunction(S s) {
        System.out.println("s type: " + s.getClass().getName());
    }

    /**
     * 泛型限定,多个限定,类需要放在首位
     *
     * @param <T> the type parameter
     * @param t1  the t 1
     * @param t2  the t 2
     */
    public static <T extends Object & Comparable & Serializable> void genericLimit(T t1, T t2) {
        System.out.println("t1 genericLimit t2 :result: " + t1.compareTo(t2));
    }

    @Override
    public void print(T t) {
        System.out.println("t type: " + t.getClass().getName());
    }
}
