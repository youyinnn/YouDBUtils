package cn.youyinnn.youDataBase;

import cn.youyinnn.youDataBase.druid.YouDruid;
import cn.youyinnn.youDataBase.druid.exception.NoDataSourceInitException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/20
 */
public class ConnectionContainer {

    private static ConnectionContainer          instance                    = new ConnectionContainer();

    private ThreadLocal<Connection>             connectionThreadLocal       = new ThreadLocal<>();

    private ConnectionContainer(){}

    public static ConnectionContainer getInstance() { return instance ; }

    public void bindConn(Connection connection) { connectionThreadLocal.set(connection);}

    public Connection getConn() {

        Connection connection = connectionThreadLocal.get();

        if (connection == null) {
            try {
                connection = YouDruid.getCurrentDataSourceConn();
                System.out.println("Bind conn :"+connection);
                bindConn(connection);
            } catch (SQLException | NoDataSourceInitException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

    public void removeConn() {connectionThreadLocal.remove();}

}
