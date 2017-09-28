package cn.youyinnn;

import cn.youyinnn.youDataBase.interfaces.YouDao;
import cn.youyinnn.youDataBase.proxy.TransactionProxyGenerator;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/27
 */
public class IocBean {

    public static final String SINGLETON = "singleton";

    public static final String PROTOTYPE = "prototype";

    private YouDao dao;

    private String scope;

    private String className;

    public IocBean(YouDao dao, String scope) {
        this.dao = dao;
        this.scope = scope;
        className = dao.getClass().getName();
    }

    public YouDao getDao(){

        switch (scope) {
            case SINGLETON:
                return dao;
            case PROTOTYPE:
                return TransactionProxyGenerator.getProxyObject(dao.getClass());
            default:
                return null;
        }
    }
}
