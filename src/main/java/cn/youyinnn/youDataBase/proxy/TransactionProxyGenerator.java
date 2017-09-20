package cn.youyinnn.youDataBase.proxy;

import cn.youyinnn.youDataBase.annotation.YouDao;
import net.sf.cglib.proxy.Enhancer;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/13
 */
public class TransactionProxyGenerator {

    public static Object getProxyObject(YouDao dao){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(dao.getClass());


        return null;
    }

}
