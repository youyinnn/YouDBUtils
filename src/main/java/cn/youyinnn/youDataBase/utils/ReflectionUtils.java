package cn.youyinnn.youDataBase.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * The type Reflection utils.
 *
 * @description:
 * @author: youyinnn
 * @date: 2017 /10/3
 */
public class ReflectionUtils {

    /**
     * 使 filed 变为可访问
     *
     * @param field the field
     */
    public static void fieldAccessible(Field field){
        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * 循环向上转型 获取对象的这个field
     *
     * @param o         the o
     * @param fieldName the field name
     * @return the declared field
     */
    public static Field getDeclaredField(Object o , String fieldName) {
        for (
                Class<?> superclass = o.getClass() ;
                superclass != Object.class ;
                superclass = superclass.getSuperclass()
                ) {
            try {
                return superclass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) {
                //Field 不在当前类定义, 继续向上转型
            }
        }
        return null;
    }

    /**
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
     *
     * @param o         the o
     * @param fieldName the field name
     * @param value     the value
     */
    public static void setFieldValue(Object o, String fieldName, Object value) {
        Field field = getDeclaredField(o, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("No such field [ "+fieldName+" ] in class [ " +o.getClass().getSimpleName()+ " ]." );
        }

        fieldAccessible(field);

        try {
            field.set(o, value);
        } catch (IllegalAccessException ignore) {

        }
    }

    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     *
     * @param o         the o
     * @param fieldName the field name
     * @return object
     */
    public static Object getFieldValue(Object o, String fieldName){
        Field field = getDeclaredField(o, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("No such field [ "+fieldName+" ] in class [ " +o.getClass().getSimpleName()+ " ]." );
        }

        fieldAccessible(field);

        Object result = null;

        try {
            result = field.get(o);
        } catch (IllegalAccessException ignore) {

        }

        return result;
    }

}
