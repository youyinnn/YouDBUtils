package cn.youyinnn.youDataBase.proxy;

import cn.youyinnn.youDataBase.interfaces.YouDao;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/13
 */
public class TransactionProxyGenerator {

    public static YouDao getProxyObject(YouDao dao){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(dao.getClass());
        CallbackFilter transactionCallbackFilter = new TransactionCallbackFilter();

        Callback doNothing = NoOp.INSTANCE;
        Callback transaction = new TransactionInterceptor();
        Callback[] callbacks = new Callback[]{doNothing, transaction};

        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackFilter(transactionCallbackFilter);

        return (YouDao) enhancer.create();
    }

}
