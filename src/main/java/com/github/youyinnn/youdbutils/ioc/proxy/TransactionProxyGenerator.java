package com.github.youyinnn.youdbutils.ioc.proxy;

import com.github.youyinnn.youdbutils.ioc.annotations.Transaction;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.annotation.Annotation;

/**
 * 事务代理类的生成器.
 * 生成过程:
 *  1.首先获取类上的Transaction注解,如果类上没有该注解,则说明该类并不是所有方法都需要包装事务;
 *  2.根据1的结果,标识一个布尔值isAll;
 *  3.根据isAll,选择生成代理对象所使用的过滤器;
 *
 * @author youyinnn
 */
public class TransactionProxyGenerator {

    public static Object getProxyObject(Class youServiceClass) {

        boolean isAll = false;

        Annotation annotation = youServiceClass.getAnnotation(Transaction.class);

        if (annotation != null) {
            isAll = true;
        }

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(youServiceClass);

        Callback doNothing = NoOp.INSTANCE;
        Callback transaction = new TransactionInterceptor();
        Callback[] callbacks = new Callback[]{doNothing, transaction};

        enhancer.setCallbacks(callbacks);

        if (isAll) {
            enhancer.setCallbackFilter(new TransactionClassCallbackFilter());
        } else {
            enhancer.setCallbackFilter(new TransactionMethodCallbackFilter());
        }

        return enhancer.create();

    }

}
