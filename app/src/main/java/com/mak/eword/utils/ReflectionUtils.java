package com.mak.eword.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jayson on 2019/4/8.
 * Content:
 */
public class ReflectionUtils {
    public static List<Field> getFields(Class<?> clz) {
        List<Field> ret = new ArrayList<>();
        for (Class<?> c = clz; c != Object.class; c = c.getSuperclass()) {
            Field[] fields = c.getDeclaredFields();
            ret.addAll(Arrays.asList(fields));
        }
        return ret;
    }

    /**
     * @param cls   子类对应的Class
     * @param index 子类继承父类时传入的索引,从0开始
     * @return
     */
    public static Class<?> getSuperClassGenericType(Class<?> cls, int index) {
        if (index < 0)
            return null;

        Type type = cls.getGenericSuperclass();
        if (!(type instanceof ParameterizedType))
            return null;

        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        if (typeArguments == null || typeArguments.length == 0 || index > typeArguments.length - 1)
            return null;

        Type t = typeArguments[index];
        if (!(t instanceof Class)) {
            return null;
        }
        return (Class<?>) t;
    }

    public static Class<?> getSuperClassGenericType(Class<?> cls) {
        return getSuperClassGenericType(cls, 0);
    }
}
