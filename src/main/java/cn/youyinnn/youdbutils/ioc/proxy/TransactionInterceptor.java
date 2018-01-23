package cn.youyinnn.youdbutils.ioc.proxy;

import cn.youyinnn.youdbutils.druid.ThreadLocalPropContainer;
import cn.youyinnn.youdbutils.ioc.annotations.Transaction;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/14
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
