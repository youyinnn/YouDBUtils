package cn.lab.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/12
 */
public class TestCglib {

    public static void main(String[] args) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TargetObject.class);
        enhancer.setCallback(new TargetInterceptor());

        TargetObject targetObject = (TargetObject) enhancer.create();

        System.out.println(targetObject.hashCode());

        //targetObject.say("aaa");
        //targetObject.a();

    }

}
