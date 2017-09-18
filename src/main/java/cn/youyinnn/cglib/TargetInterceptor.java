package cn.youyinnn.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/12
 */
public class TargetInterceptor implements MethodInterceptor {

    /**
     * 定义一个拦截器
     *  在调用方法的时候 cglib会回调MethodInterceptor接口方法拦截
     *
     *  参数o：为由Cglib动态生成的代理类实例（这个对象最好不要操作）
     *  参数method：为上文中实体类所调用的被代理的方法引用
     *  参数objects：参数列表
     *  参数methodProxy：为生成代理类对方法的引用
     *
     * */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        System.out.println("---------调用前---------");

        Object result = methodProxy.invokeSuper(o,objects);

        System.out.println("---------调用后---------");

        return result;
    }
}
