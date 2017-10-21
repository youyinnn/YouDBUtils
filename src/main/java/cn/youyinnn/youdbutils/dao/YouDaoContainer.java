package cn.youyinnn.youdbutils.dao;

import java.util.HashMap;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/10/21
 */
public class YouDaoContainer {

    private static HashMap<Class,YouDao> youDaoHashMap = new HashMap<>();

    public static YouDao getDao(Class daoClass) {

        YouDao dao = null;

        if (youDaoHashMap.containsKey(daoClass)) {
            dao = youDaoHashMap.get(daoClass);
        } else {
            try {
                dao = (YouDao) daoClass.newInstance();
                youDaoHashMap.put(daoClass,dao);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return dao;
    }
}
