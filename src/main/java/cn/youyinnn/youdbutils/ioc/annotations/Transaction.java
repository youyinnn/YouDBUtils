package cn.youyinnn.youdbutils.ioc.annotations;

import cn.youyinnn.youdbutils.ioc.ServiceIocBean;

import java.lang.annotation.*;

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
