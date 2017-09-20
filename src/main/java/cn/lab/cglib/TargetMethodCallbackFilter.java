package cn.lab.cglib;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/12
 */
public class TargetMethodCallbackFilter implements CallbackFilter {

    /**
     * return值为被代理类的各个方法在回调数组Callback[]中的位置索引
     * */
    @Override
    public int accept(Method method) {

        if (method.getName().equals("a")) {
            //System.out.println(method.getDeclaringClass().getName());
            return 0;
        }
        if (method.getName().equals("b")) {
            //System.out.println("method b");
            return 1;
        }
        if (method.getName().equals("c")) {
            //System.out.println("method c");
            return 2;
        }

        return 0;
    }
}
