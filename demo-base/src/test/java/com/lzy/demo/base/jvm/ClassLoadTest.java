package com.lzy.demo.base.jvm;

import com.lzy.demo.base.bean.SubClass;
import com.lzy.demo.base.jvm.classload.CustomClassLoader;
import org.junit.jupiter.api.Test;

public class ClassLoadTest {

    /**
     * 测试类加载器
     */
    @Test
    public void testParentLoader() {
        //AppClassLoader
        System.out.println("class loader: " + SubClass.class.getClassLoader());
        //ExtClassLoader
        System.out.println("parent loader:" + SubClass.class.getClassLoader().getParent());
        //BootstrapClassLoader,是C++编写的,所以java显示为null
        System.out.println("parent parent loader:" + SubClass.class.getClassLoader().getParent().getParent());
    }

    /**
     * 测试自定义类加载器
     *
     * @throws Exception the exception
     */
    @Test
    public void testCustomClassLoader() throws Exception {
        CustomClassLoader classLoader = new CustomClassLoader();
        //这里的名称要跟class文件中的名称一致
        Class clazz = classLoader.loadClass(ClassLoadTest.class.getName());
        Object object = clazz.newInstance();
        //这个判断不会相等,因为是不同类加载器加载的
        System.out.println("object instanceof ClassLoadTest: " + (object instanceof ClassLoadTest));
        System.out.println("object: " + object);
    }
}
