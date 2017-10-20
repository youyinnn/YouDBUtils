package cn.youyinnn.youdbutils.ioc;

import cn.youyinnn.youdbutils.ioc.proxy.TransactionProxyGenerator;

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

    static void addSingletonYouService(ServiceIocBean serviceBean) {

        String className = serviceBean.getClassName();
        serviceBean.setSingleton(TransactionProxyGenerator.getProxyObject(serviceBean.getServiceClass()));
        singletonServiceMap.put(className,serviceBean);
    }

    static void addPrototypeYouService(ServiceIocBean serviceBean) {

        String className = serviceBean.getClassName();
        prototypeServiceMap.put(className,serviceBean);
    }

    public static Object getYouService(Class serviceClass){

        ServiceIocBean serviceIocBean = singletonServiceMap.get(serviceClass.getName());

        if (serviceIocBean == null) {
            serviceIocBean = prototypeServiceMap.get(serviceClass.getName());
            if (serviceIocBean == null) {
                return null;
            } else {
                return TransactionProxyGenerator.getProxyObject(serviceIocBean.getServiceClass());
            }
        } else {
            return serviceIocBean.getSingleton();
        }
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

}
