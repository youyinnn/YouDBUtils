package cn.youyinnn.youdbutils.ioc;

import cn.youyinnn.youdbutils.exceptions.AutowiredLimitedException;
import cn.youyinnn.youdbutils.ioc.annotations.Scope;
import cn.youyinnn.youdbutils.ioc.annotations.Transaction;
import cn.youyinnn.youdbutils.ioc.proxy.TransactionProxyGenerator;

import java.lang.reflect.Method;
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

    static void addSingletonYouService(ServiceIocBean serviceBean) throws AutowiredLimitedException {

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

    static void addPrototypeYouService(ServiceIocBean serviceBean) {

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
                    return TransactionProxyGenerator.getProxyObject(serviceIocBean.getServiceClass());
                } else {
                    try {
                        return serviceClass.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            return serviceIocBean.getSingleton();
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

    public static void setYouService(Class<?> aClass) {
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
            System.out.println("set       "+ aClass);
        }
    }

}
