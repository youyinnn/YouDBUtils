package com.github.youyinnn.youdbutils.ioc.proxy;

import com.github.youyinnn.youdbutils.YouDbManager;
import com.github.youyinnn.youdbutils.druid.ThreadLocalPropContainer;
import com.github.youyinnn.youdbutils.ioc.annotations.Transaction;
import com.github.youyinnn.youdbutils.ioc.annotations.YouService;
import com.github.youyinnn.youwebutils.third.Log4j2Helper;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * 这是使用cglib代理进行事务过程代理的主过程.
 * 过程完全在一条数据库连接下进行.
 * 过程:
 *  1.先获取方法所属类的YouService注解, 根据注解索引service类所服务的数据源
 *  2.记录当次connection中所包含的根service方法token, 以便包裹多个service方法来一次性释放连接
 *  3.获取连接, 开启事务
 *  4.先在方法上扫描Transaction注解,若没有则去方法所属的类上扫描;
 *  5.获取Transaction注解上的allowNoneffectiveUpdate标识, 若不存在注解,则使用默认值;
 *  6.invoke方法;
 *  7.查询当前线程的回滚flag,若需要回滚,则回滚事务;
 *  8.提交事务,关闭连接,移除线程绑定变量;
 *
 * @author youyinnn
 */
public class TransactionInterceptor implements MethodInterceptor{

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Logger connectionLog = Log4j2Helper.getLogger("$db_connection");
        YouService service = o.getClass().getAnnotation(YouService.class);
        String dataSourceName = service.dataSourceName();
        YouDbManager.checkDataSourceName(dataSourceName);
        boolean embeddedLogEnable = YouDbManager.isYouDruidLogEnable(dataSourceName);

        String transactionRootServiceMethodName = ThreadLocalPropContainer.getTransactionRootServiceMethodName();
        long callNano = System.nanoTime();
        if (transactionRootServiceMethodName == null) {
            transactionRootServiceMethodName = method.getName() + callNano;
            ThreadLocalPropContainer.bindTransactionRootServiceMethodName(transactionRootServiceMethodName);
        }

        Connection conn = ThreadLocalPropContainer.getThreadConnection(dataSourceName);
        if (embeddedLogEnable) {
            connectionLog.info("业务连接获取:" + conn.toString().split("@")[1] +", 所属业务:{}, 调用方法{}.",
                    ThreadLocalPropContainer.getTransactionRootServiceMethodName(),
                    method.getName());
        }
        conn.setAutoCommit(false);

        Transaction transactionA = method.getAnnotation(Transaction.class)  != null ?
                method.getAnnotation(Transaction.class) : method.getDeclaringClass().getAnnotation(Transaction.class);

        boolean allowNoneffectiveUpdate = transactionA == null || transactionA.allowNoneffectiveUpdate();

        ThreadLocalPropContainer.setNoneffectiveUpdateFlag(allowNoneffectiveUpdate);

        Object result = methodProxy.invokeSuper(o,objects);

        if (ThreadLocalPropContainer.getRollbackFlag()) {
            ThreadLocalPropContainer.setRollbackFlagFalse();
            conn.rollback();
        }

        conn.commit();

        if (transactionRootServiceMethodName.equalsIgnoreCase(method.getName() + callNano)) {
            if (embeddedLogEnable) {
                connectionLog.info("业务连接释放:" + conn.toString().split("@")[1] +", 所属业务:{}, 调用方法{}.",
                        ThreadLocalPropContainer.getTransactionRootServiceMethodName(),
                        method.getName());
            }
            ThreadLocalPropContainer.removeAllThreadProp();
            ThreadLocalPropContainer.release(null, null, conn);
        }

        return result;
    }
}
