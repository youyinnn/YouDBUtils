package com.github.youyinnn.youdbutils.ioc;

/**
 * 每个YouService在Ioc容器中注册之前,需要将一些必要的信息整合起来;
 * 对于每一个需要Ioc来管理的YouService来说,我们需要保存:
 *  1.单例还是多例;
 *  2.YouService的Class对象;
 *  3.改YouService的单例对象(如果是);
 *
 * @author youyinnn
 */
public class ServiceIocBean {

    public static final String                  SINGLETON                   = "singleton";

    public static final String                  PROTOTYPE                   = "prototype";

    public static final String                  PROPAGATION_REQUIRED        = "propagation_required";

    public static final String                  PROPAGATION_REQUIRED_NEW    = "propagation_required_new";

    private Class                               serviceClass;

    private String                              scope;

    private Object                              singleton;

    public Object getSingleton() {
        return singleton;
    }

    public void setSingleton(Object singleton) {
        this.singleton = singleton;
    }

    public ServiceIocBean(Class daoClass, String scope) {
        this.serviceClass = daoClass;
        this.scope = scope;
    }

    public Class getServiceClass() {
        return serviceClass;
    }

    public String getScope() {
        return scope;
    }

    public String getClassName() {
        return serviceClass.getName();
    }

    @Override
    public String toString() {
        return "ServiceIocBean{" +
                "serviceClass=" + serviceClass +
                ", scope='" + scope + '\'' +
                ", singleton=" + singleton +
                '}';
    }
}
