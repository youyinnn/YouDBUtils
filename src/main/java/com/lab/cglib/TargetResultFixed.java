package com.lab.cglib;

import net.sf.cglib.proxy.FixedValue;

/**
 *
 * @author youyinnn
 *
 */
public class TargetResultFixed implements FixedValue {

    @Override
    public Object loadObject() throws Exception {

        System.out.println("锁定结果");

        return 999;
    }
}
