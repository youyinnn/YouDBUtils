package cn.youyinnn.youdbutils.ioc;

import cn.youyinnn.youdbutils.interfaces.YouDao;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/27
 */
public class DaoIocBean {

    public static final String                  SINGLETON                   = "singleton";

    public static final String                  PROTOTYPE                   = "prototype";

    private Class<YouDao>                       daoClass;

    private String                              scope;

    private String                              className;

    private YouDao                              singleton;

    public YouDao getSingleton() {
        return singleton;
    }

    public void setSingleton(YouDao singleton) {
        this.singleton = singleton;
    }

    public DaoIocBean(Class<YouDao> daoClass, String scope) {
        this.daoClass = daoClass;
        this.scope = scope;
        this.className = daoClass.getName();
    }

    public Class<YouDao> getDaoClass() {
        return daoClass;
    }

    public String getScope() {
        return scope;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        return "DaoIocBean{" +
                "daoClass=" + daoClass +
                ", scope='" + scope + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
