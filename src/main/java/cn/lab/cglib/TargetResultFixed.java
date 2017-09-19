package cn.lab.cglib;

import net.sf.cglib.proxy.FixedValue;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/12
 */
public class TargetResultFixed implements FixedValue {

    @Override
    public Object loadObject() throws Exception {

        System.out.println("锁定结果");

        return 999;
    }
}
