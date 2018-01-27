package com.lab.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 *
 * @author youyinnn
 *
 */
public class TestCglibFilter {

    public static void main(String[] args) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TargetObject.class);
        CallbackFilter callbackFilter = new TargetMethodCallbackFilter();

        /*
            (1)callback1：方法拦截器
            (2)NoOp.INSTANCE：这个NoOp表示no operator，即什么操作也不做，代理类直接调用被代理的方法不进行拦截。
            (3)FixedValue：表示锁定方法返回值，无论被代理类的方法返回什么值，回调方法都返回固定值。
         */
        Callback noopCb = NoOp.INSTANCE;
        Callback callback1 = new TargetInterceptor();
        Callback fixedValue = new TargetResultFixed();
        Callback[] cbArray = new Callback[]{noopCb,callback1,fixedValue};

        enhancer.setCallbacks(cbArray);
        enhancer.setCallbackFilter(callbackFilter);

        TargetObject targetObject = (TargetObject) enhancer.create();

        //targetObject.say("bbb");
        //
        //targetObject.a();
        //targetObject.b();
        //System.out.println(targetObject.c());

        System.out.println(targetObject);

    }

}
