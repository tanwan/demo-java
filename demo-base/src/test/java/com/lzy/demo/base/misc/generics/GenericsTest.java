package com.lzy.demo.base.misc.generics;

import com.lzy.demo.base.bean.GenericsClass;
import com.lzy.demo.base.bean.GenericsSubClass;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;



public class GenericsTest {
    private GenericsClass<String> genericsClass = new GenericsClass<>();

    /**
     * 测试泛型基本用法
     */
    @Test
    public void testGenerics() {
        genericsClass.setValue("hello world");
        System.out.println(genericsClass.getValue());
        genericsClass.print("hello world");
        //泛型方法
        genericsClass.genericFunction(genericsClass);
        GenericsClass.genericLimit("a", "b");
    }


    /**
     * 使用反射验证泛型只有编译阶段有用
     *
     * @throws Exception the exception
     */
    @Test
    public void testReflectGenerics() throws Exception {
        List<String> strList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        System.out.println("strList reflect type: " + strList.getClass().getName());
        System.out.println("integerList reflect type:" + integerList.getClass().getName());

        //反射验证 泛型只在编译阶段有用: genericClass的类型参数是String 但是用反射可以把其它类型的参数传进去而不报错
        Method method = genericsClass.getClass().getMethod("print", Object.class);
        //原本只能传String的,但是用反射可以传int
        method.invoke(genericsClass, 123);
        //把List<Integer>赋值给List,就可以直接插入String
        List list = integerList;
        list.add("aaa");
        System.out.println(list);
    }

    /**
     * 测试获取泛型类的实际类型
     */
    @Test
    public void testActualType() {
        new GenericsSubClass();
    }
}
