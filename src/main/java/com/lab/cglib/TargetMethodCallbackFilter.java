package com.lab.cglib;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 *
 * @author youyinnn
 *
 */
public class TargetMethodCallbackFilter implements CallbackFilter {

    /**
     * return值为被代理类的各个方法在回调数组Callback[]中的位置索引
     * */
    @Override
    public int accept(Method method) {

        if (method.toString().contains("java.lang.Object")){

            return 0;
        }

        if ("a".equals(method.getName())) {
            return 0;
        }
        if ("b".equals(method.getName())) {
            //System.out.println("method b");
            return 1;
        }
        if ("c".equals(method.getName())) {
            //System.out.println("method c");
            return 2;
        }

        return 0;
    }
}
