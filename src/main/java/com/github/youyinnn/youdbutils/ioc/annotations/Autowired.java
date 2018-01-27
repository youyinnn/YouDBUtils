package com.github.youyinnn.youdbutils.ioc.annotations;

import java.lang.annotation.*;

/**
 * 自动装配注解.
 * 目前只能在YouService类中使用它来标识需要装配:
 *  1.YouService类对象.
 *  2.YouDao类对象.
 * 使用该类时需要确保:
 *  1.只有使用@YouService注解过的类才能自动装配其field,或者被装配;
 *  2.只有继承了YouDao的Dao类才能被自动装配;
 */
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
}
