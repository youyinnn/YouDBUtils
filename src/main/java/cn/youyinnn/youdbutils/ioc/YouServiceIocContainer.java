package cn.youyinnn.youdbutils.ioc;

import cn.youyinnn.youdbutils.dao.YouDao;
import cn.youyinnn.youdbutils.dao.YouDaoContainer;
import cn.youyinnn.youdbutils.exceptions.AutowiredLimitedException;
import cn.youyinnn.youdbutils.ioc.annotations.Autowired;
import cn.youyinnn.youdbutils.ioc.annotations.Scope;
import cn.youyinnn.youdbutils.ioc.annotations.Transaction;
import cn.youyinnn.youdbutils.ioc.annotations.YouService;
import cn.youyinnn.youdbutils.ioc.proxy.TransactionProxyGenerator;
import cn.youyinnn.youdbutils.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/10
 */
public class YouServiceIocContainer {

    private static HashMap<String,ServiceIocBean>              prototypeServiceMap             = new HashMap<>();
    private static HashMap<String,ServiceIocBean>              singletonServiceMap             = new HashMap<>();

    private YouServiceIocContainer() {}

    private static void addSingletonYouService(ServiceIocBean serviceBean) throws AutowiredLimitedException {

        String className = serviceBean.getClassName();
        Class serviceClass = serviceBean.getServiceClass();
        if (hasTransactionAnnotation(serviceClass)) {
            serviceBean.setSingleton(TransactionProxyGenerator.getProxyObject(serviceBean.getServiceClass()));
        } else {
            try {
                serviceBean.setSingleton(serviceClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        singletonServiceMap.put(className,serviceBean);
    }

    private static void addPrototypeYouService(ServiceIocBean serviceBean) {

        String className = serviceBean.getClassName();
        prototypeServiceMap.put(className,serviceBean);
    }

    public static Object getYouService(Class serviceClass) throws AutowiredLimitedException {

        ServiceIocBean serviceIocBean = singletonServiceMap.get(serviceClass.getName());

        if (serviceIocBean == null) {
            serviceIocBean = prototypeServiceMap.get(serviceClass.getName());
            if (serviceIocBean == null) {
                return null;
            } else {
                if (hasTransactionAnnotation(serviceClass)) {
                    return autowired(TransactionProxyGenerator.getProxyObject(serviceIocBean.getServiceClass()));
                } else {
                    try {
                        return autowired(serviceClass.newInstance());
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            return autowired(serviceIocBean.getSingleton());
        }

        return null;
    }

    public static void showServiceMap(){
        System.out.println("[ prototype service ]:");
        for (Map.Entry<String, ServiceIocBean> stringIocBeanEntry : prototypeServiceMap.entrySet()) {
            System.out.println(stringIocBeanEntry.getKey()+" : "+stringIocBeanEntry.getValue());
        }
        System.out.println("[ singleton service ]:");

        for (Map.Entry<String, ServiceIocBean> stringIocBeanEntry : singletonServiceMap.entrySet()) {
            System.out.println(stringIocBeanEntry.getKey()+" : "+stringIocBeanEntry.getValue());
        }
    }

    private static boolean hasTransactionAnnotation(Class youService) {

        if (youService.getAnnotation(Transaction.class) == null) {
            Method[] declaredMethods = youService.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                if (declaredMethod.getAnnotation(Transaction.class) != null){
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    private static boolean hasYouService(Class youService) {
        return singletonServiceMap.containsKey(youService.getName()) | prototypeServiceMap.containsKey(youService.getName());
    }

    private static Object autowired(Object youService) throws AutowiredLimitedException {
        // Autowired实现 只接受YouDao和YouService的自动装配
        ArrayList<Field> declaredFields = ReflectionUtils.getDeclaredFields(youService, Autowired.class);
        for (Field declaredField : declaredFields) {
            Class<?> type = declaredField.getType();
            YouService youServiceAnnotation = type.getAnnotation(YouService.class);
            // 装载YouDao和YouService
            if (youServiceAnnotation == null) {
                if ("YouDao".equals(type.getSuperclass().getSimpleName())) {
                    YouDao autowiredDao = YouDaoContainer.getDao(type);
                    ReflectionUtils.setFieldValue(youService,declaredField.getName(),autowiredDao);
                } else {
                    throw new AutowiredLimitedException("不支持的自动装配类型：["+type.getSimpleName()+" "+declaredField.getName()+"].");
                }
            } else {
                Object autowiredDaoYouService = YouServiceIocContainer.getYouService(type);
                ReflectionUtils.setFieldValue(youService,declaredField.getName(),autowiredDaoYouService);
            }
        }
        return youService;
    }

    static void setYouService(Class<?> aClass) {
        Scope scope = aClass.getAnnotation(Scope.class);

        if (!YouServiceIocContainer.hasYouService(aClass)) {
            // 单例service
            if (scope == null || scope.value().equals(ServiceIocBean.SINGLETON)){
                try {
                    YouServiceIocContainer.addSingletonYouService(new ServiceIocBean(aClass, ServiceIocBean.SINGLETON));
                } catch (AutowiredLimitedException e) {
                    e.printStackTrace();
                }
            } else {
                YouServiceIocContainer.addPrototypeYouService(new ServiceIocBean(aClass, ServiceIocBean.PROTOTYPE));
            }
        }
    }

}
