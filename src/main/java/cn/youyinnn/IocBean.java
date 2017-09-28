package cn.youyinnn;

import cn.youyinnn.youDataBase.interfaces.YouDao;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/27
 */
public class IocBean {

    public static final String SINGLETON = "singleton";

    public static final String PROTOTYPE = "prototype";

    private Class<YouDao> daoClass;

    private String scope;

    private String className;


    public IocBean(Class<YouDao> daoClass, String scope) {
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
        return "IocBean{" +
                "daoClass=" + daoClass +
                ", scope='" + scope + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
