package com.github.youyinnn.youdbutils.ioc.proxy;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 * cglib代理过滤器.
 * 这是事务的类级别的过滤器.
 *
 * @author youyinnn
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

        String name = method.getName();

        if ("clone".equals(name) ||
                "equals".equals(name) ||
                "finalize".equals(name) ||
                "getClass".equals(name) ||
                "hashCode".equals(name) ||
                "notify".equals(name) ||
                "notifyAll".equals(name) ||
                "toString".equals(name) ||
                "wait".equals(name)) {
            return 0;
        }

        return 1;
    }
}
