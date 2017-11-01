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

    private static ThreadLocalPropContainer         instance                        = new ThreadLocalPropContainer();

    private ThreadLocal<Connection>                 threadConnection                = new ThreadLocal<>();

    private ThreadLocal<Boolean>                    threadNeedToRollback            = new ThreadLocal<>();

    private ThreadLocal<Boolean>                    threadAllowNoneffectiveUpdate   = new ThreadLocal<>();

    private ThreadLocalPropContainer(){
        threadNeedToRollback.set(false);
    }

    public static ThreadLocalPropContainer getInstance() { return instance ; }

    private void bindConn(Connection connection) { threadConnection.set(connection);}

    public void setRollbackFlagTrue() {
        threadNeedToRollback.set(true);
    }

    public void setRollbackFlagFalse() {
        threadNeedToRollback.set(false);
    }

    public void setNoneffectiveUpdateFlag(boolean flag) {
        threadAllowNoneffectiveUpdate.set(flag);
    }

    public Connection getThreadConnection() {

        Connection connection = threadConnection.get();

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

    public Boolean getRollbackFlag() {
        return threadNeedToRollback.get();
    }

    public Boolean getNoneffectiveUpdateFlag() {
        return threadAllowNoneffectiveUpdate.get();
    }
    public void removeRollbackFlag() {threadNeedToRollback.remove();}

    public void removeThreadConnection() {threadConnection.remove();}

    public void removeNoneffectiveUpdateFlag() {threadAllowNoneffectiveUpdate.remove();}

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
