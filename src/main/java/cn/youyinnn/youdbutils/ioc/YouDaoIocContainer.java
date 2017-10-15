package cn.youyinnn.youdbutils.ioc;

import cn.youyinnn.youdbutils.interfaces.YouDao;
import cn.youyinnn.youdbutils.ioc.proxy.TransactionProxyGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/10
 */
public class YouDaoIocContainer {

    private static HashMap<String,DaoIocBean>              prototypeDaoMap             = new HashMap<>();
    private static HashMap<String,DaoIocBean>              singletonDaoMap             = new HashMap<>();

    private YouDaoIocContainer() {}

    public static void addSingletonYouDao(DaoIocBean daoBean) {

        String className = daoBean.getClassName();
        daoBean.setSingleton(TransactionProxyGenerator.getProxyObject(daoBean.getDaoClass()));
        singletonDaoMap.put(className,daoBean);
    }

    public static void addPrototypeYouDao(DaoIocBean daoBean) {

        String className = daoBean.getClassName();
        prototypeDaoMap.put(className,daoBean);
    }

    public static YouDao getYouDao(String className){

        DaoIocBean daoIocBean = singletonDaoMap.get(className);

        if (daoIocBean == null) {
            daoIocBean = prototypeDaoMap.get(className);
            return TransactionProxyGenerator.getProxyObject(daoIocBean.getDaoClass());
        } else {
            return daoIocBean.getSingleton();
        }
    }

    public static void showDaoMap(){
        for (Map.Entry<String, DaoIocBean> stringIocBeanEntry : prototypeDaoMap.entrySet()) {
            System.out.println(stringIocBeanEntry.getKey()+" : "+stringIocBeanEntry.getValue());
        }
        System.out.println("------------------------------");

        for (Map.Entry<String, DaoIocBean> stringIocBeanEntry : singletonDaoMap.entrySet()) {
            System.out.println(stringIocBeanEntry.getKey()+" : "+stringIocBeanEntry.getValue());
        }
    }

}
