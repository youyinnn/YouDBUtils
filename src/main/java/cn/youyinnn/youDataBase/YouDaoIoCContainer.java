package cn.youyinnn.youDataBase;

import cn.youyinnn.youDataBase.interfaces.YouDao;

import java.util.HashMap;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/10
 */
public class YouDaoIoCContainer {

    private static HashMap<String,YouDao> daoMap = new HashMap<>();;

    private YouDaoIoCContainer() {}

    public static YouDao addYouDao(String key, YouDao dao) {
        return daoMap.put(key,dao);
    }

    public static YouDao getYouDaoByKey(String keyStr){
        return daoMap.get(keyStr);
    }

    public static YouDao getYouDaoByClassName(String className){
        for (YouDao dao : daoMap.values()) {
            String daoClassName = dao.getClass().getName();
            if (daoClassName.equals(className)){
                return dao;
            }
        }
        return null;
    }


}
