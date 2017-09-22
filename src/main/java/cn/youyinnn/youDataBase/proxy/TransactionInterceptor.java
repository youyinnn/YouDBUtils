package cn.youyinnn.youDataBase.proxy;

import cn.youyinnn.youDataBase.ConnectionContainer;
import cn.youyinnn.youDataBase.SqlExecuteHandler;
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

        Connection conn = ConnectionContainer.getInstance().getConn();
        conn.setAutoCommit(false);
        Object result = methodProxy.invokeSuper(o,objects);

        if (SqlExecuteHandler.isRollback) {
            SqlExecuteHandler.isRollback = false;
            conn.rollback();
        }

        conn.commit();

        return result;
    }
}
