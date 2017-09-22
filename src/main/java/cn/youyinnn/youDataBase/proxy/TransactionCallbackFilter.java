package cn.youyinnn.youDataBase.proxy;

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
public class TransactionCallbackFilter implements CallbackFilter{

    /**
     *
     * 设置基代理过滤
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
                        if(annotation.toString().contains("cn.youyinnn.youDataBase.annotations.Transaction")) {
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
