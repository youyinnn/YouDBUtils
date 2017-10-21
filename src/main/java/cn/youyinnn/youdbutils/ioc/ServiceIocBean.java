package cn.youyinnn.youdbutils.ioc;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/27
 */
public class ServiceIocBean {

    public static final String                  SINGLETON                   = "singleton";

    public static final String                  PROTOTYPE                   = "prototype";

    public static final String                  PROPAGATION_REQUIRED        = "propagation_required";

    public static final String                  PROPAGATION_REQUIRED_NEW    = "propagation_required_new";

    private Class                               serviceClass;

    private String                              scope;

    private String                              className;

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
        this.className = daoClass.getName();
    }

    public Class getServiceClass() {
        return serviceClass;
    }

    public String getScope() {
        return scope;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        return "ServiceIocBean{" +
                "serviceClass=" + serviceClass +
                ", scope='" + scope + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
