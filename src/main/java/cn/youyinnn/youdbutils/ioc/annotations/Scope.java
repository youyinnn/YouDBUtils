package cn.youyinnn.youdbutils.ioc.annotations;

import cn.youyinnn.youdbutils.ioc.ServiceIocBean;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {

    String value() default ServiceIocBean.SINGLETON;
}
