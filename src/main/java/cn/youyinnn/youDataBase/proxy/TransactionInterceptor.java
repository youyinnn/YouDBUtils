package cn.youyinnn.youDataBase.proxy;

import cn.youyinnn.youDataBase.ConnectionContainer;
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

        System.out.println("绑定了事务的操作");

        Connection conn = ConnectionContainer.getInstance().getConn();

        conn.setAutoCommit(false);

        Object result = null;

        try {
             result = methodProxy.invokeSuper(o,objects);

             conn.commit();
        } catch (Exception e) {
            System.out.println("roll back");
            conn.rollback();
        }

        return result;
    }
}
