package cn.youyinnn.youdbutils.ioc.annotations;

import cn.youyinnn.youdbutils.ioc.ServiceIocBean;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Transaction {

    String transactionSpread() default ServiceIocBean.PROPAGATION_REQUIRED;

    boolean allowNoneffectUpdate() default true;
}
