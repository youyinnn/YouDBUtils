package cn.youyinnn.youdbutils.ioc;

import cn.youyinnn.youdbutils.dao.YouDao;
import cn.youyinnn.youdbutils.dao.YouDaoContainer;
import cn.youyinnn.youdbutils.exceptions.AutowiredException;
import cn.youyinnn.youdbutils.ioc.annotations.Autowired;
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
 * YouService的Ioc容器管理.
 * 分为单例容器和多例容器.
 * 提供:
 *  1.注册单例/多例YouService的方法;
 *  2.获取YouService对象的方法;
 *  3.查看当前Ioc内注册过的YouService的方法;
 *
 * @author youyinnn
 */
public class YouServiceIocContainer {

    private static HashMap<String,ServiceIocBean>              prototypeServiceMap             = new HashMap<>();
    private static HashMap<String,ServiceIocBean>              singletonServiceMap             = new HashMap<>();

    private YouServiceIocContainer() {}

    private static void addSingletonYouService(ServiceIocBean serviceBean) {

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

    /**
     * Gets you service.
     *
     * @param serviceClass the service class
     * @return the you service
     * @throws AutowiredException the autowired limited exception
     */
    public static Object getYouService(Class serviceClass) throws AutowiredException {

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
                        return null;
                    }
                }
            }
        } else {
            return autowired(serviceIocBean.getSingleton());
        }
    }

    /**
     * Show service map.
     */
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

    /**
     * 检查该YouService是否在类上或者方法中拥有Transaction注释 也即这个方法决定是否生成代理bean还是原生bean
     * @param youService
     * @return
     */
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

    /**
     * 这个方法检查当前ioc容器中是否已经存在该YouService
     * @param youService
     * @return
     */
    private static boolean hasYouService(Class youService) {
        return singletonServiceMap.containsKey(youService.getName()) | prototypeServiceMap.containsKey(youService.getName());
    }

    /**
     * 对于youService对象的自动装配方法 只装配Dao和YouService
     * @param youService
     * @return
     * @throws AutowiredException
     */
    private static Object autowired(Object youService) throws AutowiredException {
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
                    throw new AutowiredException("不支持的自动装配类型：["+type.getSimpleName()+" "+declaredField.getName()+"].");
                }
            } else {
                Object autowiredDaoYouService = getYouService(type);
                ReflectionUtils.setFieldValue(youService,declaredField.getName(),autowiredDaoYouService);
            }
        }
        return youService;
    }

    /**
     * 注册YouService到Ioc容器中.
     *
     * @param aClass the a class
     */
    static void registerYouService(Class<?> aClass) {
        YouService youService = aClass.getAnnotation(YouService.class);

        if (!hasYouService(aClass)) {
            // 单例service
            if (youService.scope().equals(ServiceIocBean.SINGLETON)){
                addSingletonYouService(new ServiceIocBean(aClass, ServiceIocBean.SINGLETON));
            } else {
                addPrototypeYouService(new ServiceIocBean(aClass, ServiceIocBean.PROTOTYPE));
            }
        }
    }

}
