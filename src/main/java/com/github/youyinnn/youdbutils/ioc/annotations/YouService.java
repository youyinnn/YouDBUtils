package com.github.youyinnn.youdbutils.ioc.annotations;

import com.github.youyinnn.youdbutils.ioc.ServiceIocBean;

import java.lang.annotation.*;

/**
 * 这个注释用于标识持有YouDao的Service类.
 * 被YouService标识的类可以被ServiceScanner.scanPackageForService方法扫描进YouServiceIocContainer.
 * 这个注解可以指定YouService类是单例还是多例.
 */
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface YouService {

    String scope() default ServiceIocBean.SINGLETON;

    String dataSourceName();
}
