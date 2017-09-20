package cn.youyinnn.youDataBase;

import java.sql.Connection;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/20
 */
public class ConnectionContainer {

    private ConnectionContainer(){}

    private static ConnectionContainer instance = new ConnectionContainer();

    public static ConnectionContainer getInstance() { return instance ; }

    private ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    public void bind(Connection connection) { connectionThreadLocal.set(connection);}

    public Connection get() { return connectionThreadLocal.get(); }

    public void remove() {connectionThreadLocal.remove();}

}
