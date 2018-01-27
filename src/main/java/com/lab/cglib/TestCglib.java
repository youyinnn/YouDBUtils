package com.lab.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 *
 * @author youyinnn
 *
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
