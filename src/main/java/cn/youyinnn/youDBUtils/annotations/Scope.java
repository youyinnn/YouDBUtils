package cn.youyinnn.youDBUtils.annotations;

import cn.youyinnn.youDBUtils.IocBean;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {

    String value() default IocBean.SINGLETON;
}
