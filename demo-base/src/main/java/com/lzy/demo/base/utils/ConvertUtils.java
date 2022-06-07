package com.lzy.demo.base.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 转换类:负责把源类型转换成目标类型
 *
 * @author LZY
 * @version v1.0
 */
public class ConvertUtils {

    /**
     * 把对象转换成int类型,如果转换失败则返回null
     *
     * @param object the object
     * @return the integer
     */
    public static Integer asInt(final Object object) {
        return asInt(object, null);
    }

    /**
     * 把对象转换成int类型,如果转换失败则返回默认值
     *
     * @param object       the object
     * @param defaultValue the default value
     * @return the int
     */
    public static Integer asInt(final Object object, final Integer defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(object.toString());
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * 把对象转换成double类型,如果转换失败则返回null
     *
     * @param object the object
     * @return the double
     */
    public static Double asDouble(final Object object) {
        return asDouble(object, null);
    }

    /**
     * 把对象转换成double类型,如果转换失败则返回默认值
     *
     * @param object       the object
     * @param defaultValue the default value
     * @return the int
     */
    public static Double asDouble(final Object object, final Double defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(object.toString());
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * 把对象转换成long类型,如果转换失败则返回null
     *
     * @param object the object
     * @return the long
     */
    public static Long asLong(Object object) {
        return asLong(object, null);
    }

    /**
     * 把对象转换成long类型,如果转换失败则返回默认值
     *
     * @param object       the object
     * @param defaultValue the default value
     * @return the long
     */
    public static Long asLong(final Object object, final Long defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(object.toString());
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * 把对象转换成string类型，如果转换失败则返回null
     *
     * @param object the object
     * @return the string
     */
    public static String asString(final Object object) {
        return object != null ? object.toString() : null;
    }

    /**
     * 把对象转换为指定的列表 目前不完善
     *
     * @param <T>    the type parameter
     * @param object the object
     * @param clazz  目标类型
     * @return the list
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> asList(final Object object, final Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        List<Object> srcList = (List<Object>) object;
        if (srcList != null) {
            if (clazz.isAssignableFrom(Integer.class)) {
                for (Object item : srcList) {
                    list.add((T) asInt(item));
                }
            }
        }
        return list;
    }
}
