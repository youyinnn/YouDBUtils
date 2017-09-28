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

    public static IocBean addYouDao(IocBean daoBean) {
        return daoMap.put(daoBean.getClassName(),daoBean);
    }

    public static IocBean getYouDaoByClassName(String className){
        return daoMap.get(className);
    }

    public static HashMap<String, IocBean> getDaoMap() {
        return daoMap;
    }
}
