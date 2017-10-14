package cn.youyinnn.youdbutils.ioc.proxy;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * The type Transaction callback filter.
 *
 * @description:
 * @author: youyinnn
 * @date: 2017 /9/22
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
                        if(annotation.toString().contains("cn.youyinnn.youdbutils.dao.annotations.Transaction")) {
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
