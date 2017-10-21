package cn.youyinnn.youdbutils.ioc.proxy;

import cn.youyinnn.youdbutils.dao.YouDao;
import cn.youyinnn.youdbutils.dao.YouDaoContainer;
import cn.youyinnn.youdbutils.ioc.annotations.Autowired;
import cn.youyinnn.youdbutils.ioc.annotations.Transaction;
import cn.youyinnn.youdbutils.utils.ReflectionUtils;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/13
 */
public class TransactionProxyGenerator {

    public static Object getProxyObject(Class youServiceClass){

        boolean isAll = false;

        Annotation annotation = youServiceClass.getAnnotation(Transaction.class);

        if (annotation != null) {
            isAll = true;
        }

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(youServiceClass);

        Callback doNothing = NoOp.INSTANCE;
        Callback transaction = new TransactionInterceptor();
        Callback[] callbacks = new Callback[]{doNothing, transaction};

        enhancer.setCallbacks(callbacks);

        if (isAll) {
            enhancer.setCallbackFilter(new TransactionClassCallbackFilter());
        } else {
            enhancer.setCallbackFilter(new TransactionMethodCallbackFilter());
        }

        Object proxyObject = enhancer.create();

        // Autowired实现
        ArrayList<Field> declaredFields = ReflectionUtils.getDeclaredFields(proxyObject, Autowired.class);
        for (Field declaredField : declaredFields) {
            Class<?> type = declaredField.getType();
            YouDao dao = YouDaoContainer.getDao(type);
            ReflectionUtils.setFieldValue(proxyObject,declaredField.getName(),dao);
        }

        return proxyObject;

    }

}
