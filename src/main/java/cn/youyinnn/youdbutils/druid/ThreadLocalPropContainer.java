package cn.youyinnn.youdbutils.druid;

import cn.youyinnn.youdbutils.YouDbManager;
import cn.youyinnn.youdbutils.exceptions.NoDataSourceInitException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/20
 */
public class ThreadLocalPropContainer {

    private static ThreadLocalPropContainer         instance                    = new ThreadLocalPropContainer();

    private ThreadLocal<Connection>                 connectionThreadLocal       = new ThreadLocal<>();

    private ThreadLocal<Boolean>                    rollbackFlagThreadLocal     = new ThreadLocal<>();

    private ThreadLocalPropContainer(){
        rollbackFlagThreadLocal.set(false);
    }

    public static ThreadLocalPropContainer getInstance() { return instance ; }

    private void bindConn(Connection connection) { connectionThreadLocal.set(connection);}

    public void setFlagTrue() {
        rollbackFlagThreadLocal.set(true);
    }

    public void setFlagFalse() {
        rollbackFlagThreadLocal.set(false);
    }

    public Connection getConn() {

        Connection connection = connectionThreadLocal.get();

        if (connection == null) {
            try {
                connection = YouDbManager.youDruid.getCurrentDataSourceConn();
                //System.out.println("Bind conn :"+connection);
                bindConn(connection);
            } catch (SQLException | NoDataSourceInitException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

    public Boolean getFlag() {
        return rollbackFlagThreadLocal.get();
    }

    public void removeFlag() {rollbackFlagThreadLocal.remove();}

    public void removeConn() {connectionThreadLocal.remove();}

    public static void release(ResultSet resultSet,Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
