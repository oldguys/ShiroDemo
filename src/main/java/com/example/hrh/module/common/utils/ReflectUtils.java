package com.example.hrh.module.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huangrenhao
 * @date 2018/8/28
 */
public class ReflectUtils {

    /**
     *  默认不更新集合信息
     * @param source
     * @param target
     */
    public static void updateFieldByClass(Object source, Object target) {
        updateFieldByClass(source.getClass(), source, target, false);
    }

    /**
     * 级联更新对象属性值，Object.class 不在更新范围
     *
     * @param clazz             类类型
     * @param source            源对象
     * @param target            更新对象
     * @param updateCollections 是否级联更新集合
     */
    public static void updateFieldByClass(Class clazz, Object source, Object target, boolean updateCollections) {

        Field[] fields = clazz.getDeclaredFields();
        Method[] methods = clazz.getDeclaredMethods();

        Map<String, Method> methodMap = new HashMap<>(methods.length);
        for (Method method : methods) {
            methodMap.put(method.getName(), method);
        }

        updateFieldMap(source, target, updateCollections, fields, methodMap);

        Class superClazz = clazz.getSuperclass();
        if (!superClazz.equals(Object.class)) {
            updateFieldByClass(superClazz, source, target, updateCollections);
        }

    }

    /**
     * 更新Filed
     *
     * @param source
     * @param target
     * @param updateCollections
     * @param fields
     * @param methodMap
     */
    private static void updateFieldMap(Object source, Object target, boolean updateCollections, Field[] fields, Map<String, Method> methodMap) {
        try {
            for (Field field : fields) {
                Method setMethod = methodMap.get(tranFieldToSetterMethodName(field.getName()));
                Method getMethod = methodMap.get(tranFieldToGetterMethodName(field.getName()));

                Object objValue = getMethod.invoke(source);
                if (objValue != null) {
                    if (objValue instanceof Collection && !updateCollections) {
                        continue;
                    }
                    setMethod.invoke(target, objValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String tranFieldToGetterMethodName(String field) {
        field = "get" + field.substring(0, 1).toUpperCase() + field.substring(1, field.length());
        return field;
    }

    private static String tranFieldToSetterMethodName(String field) {
        field = "set" + field.substring(0, 1).toUpperCase() + field.substring(1, field.length());
        return field;
    }
}
