package com.github.youyinnn.youdbutils.dao;

import java.util.HashMap;

/**
 * 装载YouDao类的容器,提供方法获取YouDao.
 *
 * @author youyinnn
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
