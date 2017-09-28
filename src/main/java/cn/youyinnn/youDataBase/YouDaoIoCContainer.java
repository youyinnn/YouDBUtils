package cn.youyinnn.youDataBase;

import cn.youyinnn.IocBean;

import java.util.HashMap;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/10
 */
public class YouDaoIoCContainer {

    private static HashMap<String,IocBean> daoMap = new HashMap<>();;

    private YouDaoIoCContainer() {}

    public static IocBean addYouDao(String key, IocBean daoBean) {
        return daoMap.put(key,daoBean);
    }

    public static IocBean getYouDaoByKey(String keyStr){
        return daoMap.get(keyStr);
    }

    //public static YouDao getYouDaoByClassName(String className){
    //
    //}


}
