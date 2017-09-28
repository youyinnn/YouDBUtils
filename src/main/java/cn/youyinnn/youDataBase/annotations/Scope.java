package cn.youyinnn.youDataBase.annotations;

import cn.youyinnn.IocBean;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {

    String value() default IocBean.SINGLETON;
}
