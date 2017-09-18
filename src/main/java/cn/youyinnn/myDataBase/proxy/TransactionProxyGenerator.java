package cn.youyinnn.myDataBase.proxy;

import cn.youyinnn.myDataBase.annotation.Dao;
import net.sf.cglib.proxy.Enhancer;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/13
 */
public class TransactionProxyGenerator {

    public static Object getProxyObject(Dao dao){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(dao.getClass());


        return null;
    }

}
