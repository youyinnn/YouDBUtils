package cn.youyinnn.youDataBase.proxy;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/23
 */
public class TransactionClassCallbackFilter implements CallbackFilter {

    /**
     *
     * 设置基代理过滤
     *
     * 这个过滤器除了继承的Object方法 其它方法都进行代理
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
                return 1;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
