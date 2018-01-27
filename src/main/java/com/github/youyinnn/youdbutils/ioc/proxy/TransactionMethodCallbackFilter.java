package com.github.youyinnn.youdbutils.ioc.proxy;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * cglib代理过滤器.
 * 这是事务的方法级别的过滤器.
 *
 * @author youyinnn
 */
public class TransactionMethodCallbackFilter implements CallbackFilter{

    /**
     *
     * 设置基代理过滤
     *
     * 这个过滤器只代理具有cn.youyinnn.youdbutils.annotations.Transaction注解的方法
     *
     * 0 : 不进行代理
     *
     * 1 : 进行代理
     *
     * */
    @Override
    public int accept(Method method) {

        Class<?> declaringClass = method.getDeclaringClass();

        try {
            if (!declaringClass.equals(Class.forName("java.lang.Object"))){
                Annotation[] annotations = method.getAnnotations();

                if (annotations.length > 0) {
                    for (Annotation annotation : annotations) {
                        if(annotation.toString().contains("Transaction")) {
                            return 1;
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
