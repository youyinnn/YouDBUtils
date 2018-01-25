package cn.youyinnn.youdbutils.ioc.annotations;

import cn.youyinnn.youdbutils.ioc.ServiceIocBean;

import java.lang.annotation.*;

/**
 * 标志事务传播的注解.
 * 该注解只限标注在YouService类上才有效;
 * 如果该注解标在类上,则类下所有方法都需要被加上事务特性;
 * 如果改注解标在类的方法上,则本类只有被标上注解的方法才有事务特性;
 *
 * 该注解目前只能指定是否允许无效更新的标识,默认允许无效更新.
 */
@Inherited
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Transaction {

    // TODO：事务的传播行为可选功能待实现
    /**
     * @return 返回事务的传播行为策略 默认一个线程有且仅有一个事务
     */
    String transactionSpread() default ServiceIocBean.PROPAGATION_REQUIRED;

    /**
     * @return 返回布尔值表示是否允许无效的update操作 如果不允许 则出现该情况的时候回报错 默认允许
     */
    boolean allowNoneffectiveUpdate() default true;
}
