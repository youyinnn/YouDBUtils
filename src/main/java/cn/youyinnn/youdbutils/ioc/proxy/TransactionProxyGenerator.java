package cn.youyinnn.youdbutils.ioc.proxy;

import cn.youyinnn.youdbutils.dao.YouDao;
import cn.youyinnn.youdbutils.dao.YouDaoContainer;
import cn.youyinnn.youdbutils.exceptions.AutowiredLimitedException;
import cn.youyinnn.youdbutils.ioc.YouServiceIocContainer;
import cn.youyinnn.youdbutils.ioc.annotations.Autowired;
import cn.youyinnn.youdbutils.ioc.annotations.Transaction;
import cn.youyinnn.youdbutils.ioc.annotations.YouService;
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

    public static Object getProxyObject(Class youServiceClass) throws AutowiredLimitedException {

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

        // Autowired实现 只接受YouDao和YouService的自动装配
        ArrayList<Field> declaredFields = ReflectionUtils.getDeclaredFields(proxyObject, Autowired.class);
        for (Field declaredField : declaredFields) {
            Class<?> type = declaredField.getType();
            YouService youServiceAnnotation = type.getAnnotation(YouService.class);
            // 装载YouDao和YouService
            if (youServiceAnnotation == null) {
                if ("YouDao".equals(type.getSuperclass().getSimpleName())) {
                    YouDao dao = YouDaoContainer.getDao(type);
                    ReflectionUtils.setFieldValue(proxyObject,declaredField.getName(),dao);
                } else {
                    throw new AutowiredLimitedException("不支持的自动装配类型：["+type.getSimpleName()+" "+declaredField.getName()+"].");
                }
            } else {
                Object youService = YouServiceIocContainer.getYouService(type);

                // 为空则意味着该service中没有@Transaction 也则意味着不需要代理而直接使用原生
                if (youService == null) {
                    YouServiceIocContainer.setYouService(type);
                }
                youService = YouServiceIocContainer.getYouService(type);

                ReflectionUtils.setFieldValue(proxyObject,declaredField.getName(),youService);
            }
        }

        return proxyObject;

    }

}
