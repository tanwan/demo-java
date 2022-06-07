package com.lzy.demo.base.jvm;

import com.lzy.demo.base.bean.SubClass;
import com.lzy.demo.base.bean.SuperClass;
import org.junit.jupiter.api.Test;

/**
 * 测试是否执行初始化阶段
 *
 * @author LZY
 * @version v1.0
 */
public class ClassInitTest {

    /**
     * 通过子类调用父类的静态变量(不是常量),只会触发父类的初始化,不会触发子类的初始化
     */
    @Test
    public void superClassValue() {
        System.out.println("test start");
        System.out.println(SubClass.getSuperStaticValue());
    }

    /**
     * 常量会在编译阶段存入常量池中,因此不会触发初始化
     */
    @Test
    public void constantValue() {
        System.out.println("test start");
        System.out.println(SubClass.SUBCLASS_STATIC_FINAL_VALUE);
        System.out.println(SubClass.SUPER_STATIC_FINAL_VALUE);
    }


    /**
     * 通过数组定义来引用类,不会触发此类的初始化
     * 这个会触发另外一个名为 [Lcom.lzy.demo.jvm.classload.SuperClass的类的初始化
     */
    @Test
    public void newArray() {
        System.out.println("test start");
        SuperClass[] superClasses = new SuperClass[10];
        System.out.println(superClasses.getClass());
    }


    /**
     * 通过子类调用父类的静态方法只会触发父类的初始化,不会触发子类的初始化
     */
    @Test
    public void superMethod() {
        System.out.println("test start");
        SubClass.superMethod();
    }

    /**
     * 子类初始化,父类的初始化会在子类之前完成
     */
    @Test
    public void subMethod() {
        System.out.println("test start");
        System.out.println(SubClass.getSubClassValue());
    }

    /**
     * 使用new会触发初始化
     */
    @Test
    public void testNew() {
        System.out.println("test start");
        SubClass superClass = new SubClass();
    }

    /**
     * 使用Class#forName()会触发初始化
     *
     * @throws ClassNotFoundException classNotFoundException
     */
    @Test
    public void testClassForName() throws ClassNotFoundException {
        System.out.println("test start");
        Class.forName("com.lzy.demo.base.bean.SubClass");
    }

    /**
     * 使用ClassLoader#loadClass()不会触发初始化
     *
     * @throws ClassNotFoundException classNotFoundException
     */
    @Test
    public void testClassLoader() throws ClassNotFoundException {
        System.out.println("test start");
        ClassLoader classLoader = this.getClass().getClassLoader();
        Class clazz = classLoader.loadClass("com.lzy.demo.base.bean.SubClass");
        //创建实例时才会初始化
        //clazz.newInstance();
    }
}
