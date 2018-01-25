package cn.youyinnn.youdbutils.ioc.proxy;

import cn.youyinnn.youdbutils.druid.ThreadLocalPropContainer;
import cn.youyinnn.youdbutils.ioc.annotations.Transaction;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * 这是使用cglib代理进行事务过程代理的主过程.
 * 过程完全在一条数据库连接下进行.
 * 过程:
 *  1.先在方法上扫描Transaction注解,若没有则去方法所属的类上扫描;
 *  2.获取Transaction注解上的allowNoneffectiveUpdate标识;
 *  3.invoke方法;
 *  4.查询当前线程的回滚flag,若需要回滚,则回滚事务;
 *  5.提交事务,关闭连接,移除线程绑定变量;
 *
 * @author youyinnn
 */
public class TransactionInterceptor implements MethodInterceptor{

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        Connection conn = ThreadLocalPropContainer.getThreadConnection();
        conn.setAutoCommit(false);

        Transaction transactionA = method.getAnnotation(Transaction.class);
        if (transactionA == null) {
            Class<?> declaringClass = method.getDeclaringClass();
            transactionA = declaringClass.getAnnotation(Transaction.class);
        }

        ThreadLocalPropContainer.setNoneffectiveUpdateFlag(transactionA.allowNoneffectiveUpdate());

        Object result = methodProxy.invokeSuper(o,objects);

        if (ThreadLocalPropContainer.getRollbackFlag()) {
            ThreadLocalPropContainer.setRollbackFlagFalse();
            conn.rollback();
        }

        conn.commit();
        conn.close();

        ThreadLocalPropContainer.removeNoneffectiveUpdateFlag();
        ThreadLocalPropContainer.removeRollbackFlag();
        ThreadLocalPropContainer.removeThreadConnection();

        return result;
    }
}
