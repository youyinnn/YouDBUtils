package cn.youyinnn.myDataBase;

import cn.youyinnn.myDataBase.annotation.Dao;

import java.util.HashMap;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/10
 */
public class MyIoCContainer {

    private static HashMap<String,Dao> daoMap = new HashMap<>();;

    private MyIoCContainer() {}

    public static Dao addDao(String key, Dao dao) {
        return daoMap.put(key,dao);
    }

    public static Dao getDaoByKey(String keyStr){
        return daoMap.get(keyStr);
    }

    public static Dao getDaoByClassName(String className){
        for (Dao dao : daoMap.values()) {
            String daoClassName = dao.getClass().getName();
            if (daoClassName.equals(className)){
                return dao;
            }
        }
        return null;
    }


}
