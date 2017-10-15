package cn.youyinnn.youdbutils.dao.annotations;

import cn.youyinnn.youdbutils.ioc.DaoIocBean;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {

    String value() default DaoIocBean.SINGLETON;
}
