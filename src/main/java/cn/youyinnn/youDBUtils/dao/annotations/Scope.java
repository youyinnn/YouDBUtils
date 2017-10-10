package cn.youyinnn.youDBUtils.dao.annotations;

import cn.youyinnn.youDBUtils.ioc.IocBean;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {

    String value() default IocBean.SINGLETON;
}
