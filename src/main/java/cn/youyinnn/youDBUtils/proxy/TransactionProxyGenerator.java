package cn.youyinnn.youDBUtils.proxy;

import cn.youyinnn.youDBUtils.interfaces.YouDao;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.annotation.Annotation;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/13
 */
public class TransactionProxyGenerator {

    public static YouDao getProxyObject(Class youDaoClass){

        Annotation[] annotations = youDaoClass.getAnnotations();

        boolean isAll = false;

        for (Annotation annotation : annotations) {
            if (annotation.annotationType().getName().equals("cn.youyinnn.youDBUtils.annotations.Transaction")){
                isAll = true;
                break;
            }
        }

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(youDaoClass);

        Callback doNothing = NoOp.INSTANCE;
        Callback transaction = new TransactionInterceptor();
        Callback[] callbacks = new Callback[]{doNothing, transaction};

        enhancer.setCallbacks(callbacks);

        if (isAll) {
            enhancer.setCallbackFilter(new TransactionClassCallbackFilter());
        } else {
            enhancer.setCallbackFilter(new TransactionMethodCallbackFilter());
        }

        return (YouDao) enhancer.create();

    }

}
