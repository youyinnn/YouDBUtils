package cn.youyinnn.youDataBase.proxy;

import cn.youyinnn.youDataBase.interfaces.YouDao;
import net.sf.cglib.proxy.Enhancer;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/13
 */
public class TransactionProxyGenerator {

    public static YouDao getProxyObject(YouDao dao){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(dao.getClass());
        enhancer.setCallback(new TransactionInterceptor());

        return (YouDao) enhancer.create();
    }

}
