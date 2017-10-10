package cn.youyinnn.youDBUtils.ioc.proxy;

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

        String name = method.getName();

        if (name.equals("toString") || name.equals("equals") || name.equals("hashCode") || name.equals("clone")) {
            return 0;
        }

        return 1;
    }
}
