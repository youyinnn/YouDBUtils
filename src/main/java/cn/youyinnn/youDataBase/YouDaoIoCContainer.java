package cn.youyinnn.youDataBase;

import cn.youyinnn.IocBean;
import cn.youyinnn.youDataBase.interfaces.YouDao;
import cn.youyinnn.youDataBase.proxy.TransactionProxyGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/10
 */
public class YouDaoIoCContainer {

    private static HashMap<String,IocBean> prototypeDaoMap = new HashMap<>();
    private static HashMap<String,IocBean> singletonDaoMap = new HashMap<>();

    private YouDaoIoCContainer() {}

    public static void addSingletonYouDao(IocBean daoBean) {

        String className = daoBean.getClassName();
        daoBean.setSingleton(TransactionProxyGenerator.getProxyObject(daoBean.getDaoClass()));
        singletonDaoMap.put(className,daoBean);
    }

    public static void addPrototypeYouDao(IocBean daoBean) {

        String className = daoBean.getClassName();
        prototypeDaoMap.put(className,daoBean);
    }

    public static YouDao getYouDao(String className){

        IocBean iocBean = singletonDaoMap.get(className);

        if (iocBean == null) {
            iocBean = prototypeDaoMap.get(className);
            return TransactionProxyGenerator.getProxyObject(iocBean.getDaoClass());
        } else {
            return iocBean.getSingleton();
        }
    }

    public static void showDaoMap(){
        for (Map.Entry<String, IocBean> stringIocBeanEntry : prototypeDaoMap.entrySet()) {
            System.out.println(stringIocBeanEntry.getKey()+" : "+stringIocBeanEntry.getValue());
        }
        System.out.println("------------------------------");

        for (Map.Entry<String, IocBean> stringIocBeanEntry : singletonDaoMap.entrySet()) {
            System.out.println(stringIocBeanEntry.getKey()+" : "+stringIocBeanEntry.getValue());
        }
    }

}
