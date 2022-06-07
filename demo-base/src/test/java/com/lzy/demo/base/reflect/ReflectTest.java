package com.lzy.demo.base.reflect;

import org.junit.jupiter.api.Test;

import java.lang.reflect.*;
import java.util.List;
import java.util.Map;

public class ReflectTest {

    /**
     * 测试反射
     *
     * @throws Exception the exception
     */
    @Test
    public void testReflect() throws Exception {
        Method method = ReflectTest.class.getMethod("method", Object.class, Map.class, List[].class, Object[].class);
        for (Type type : method.getGenericParameterTypes()) {
            getActualTypeArguments(type, true);
        }
    }

    private static void getActualTypeArguments(Type type, boolean showLine) throws Exception {
        System.out.println("this type is :" + type);
        if (type instanceof ParameterizedType) {
            // 参数化类型
            System.out.println("type instanceof ParameterizedType");
            Type[] typeArguments = ((ParameterizedType) type).getActualTypeArguments();
            //递归获取参数化类型的实际类型
            for (Type t : typeArguments) {
                getActualTypeArguments(t, false);
            }
        } else if (type instanceof GenericArrayType) {
            // 参数化类型数组或类型变量数组
            System.out.println("type instanceof GenericArrayType");
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            getActualTypeArguments(componentType, false);
        } else if (type instanceof TypeVariable) {
            //类型变量
            System.out.println("type instanceof TypeVariable");
        } else if (type instanceof WildcardType) {
            //通配符表态式
            System.out.println("type instanceof WildcardType");
            for (Type t : ((WildcardType) type).getLowerBounds()) {
                getActualTypeArguments(t, false);
            }
            for (Type t : ((WildcardType) type).getUpperBounds()) {
                getActualTypeArguments(t, false);
            }
        } else if (type instanceof Class) {
            //类型
            System.out.println("type instanceof Class");
        } else {
            throw new Exception();
        }
        if (showLine) {
            System.out.println("--------------------------------------------------------");
        }
    }

    public <T> void method(T arg0, Map<String, ? extends T> arg1, List<? extends T>[] arg2, T[] arg3) {

    }

}
