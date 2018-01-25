package cn.youyinnn.youdbutils.druid;

import cn.youyinnn.youdbutils.YouDbManager;
import cn.youyinnn.youdbutils.exceptions.NoDataSourceInitException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 提供线程级变量的保存容器.
 *
 * @author youyinnn
 */
public class ThreadLocalPropContainer {

    /**
     * 当前线程和一条数据库连接绑定
     */
    private static ThreadLocal<Connection>          threadConnection                = new ThreadLocal<>();

    /**
     * 当前线程和唯一的一个布尔值绑定
     * 这个布尔值确定当前sql连接是否需要回滚 默认是false
     * 每当我们的SqlExecutor执行到的插入/更新sql语句有错的时候
     * 在catch块可见我把该值设置为true 在commit之前rollback 完了之后setFalse
     */
    private static ThreadLocal<Boolean>             threadNeedToRollback            = new ThreadLocal<>();

    /**
     * 当前线程和唯一的一个布尔值绑定
     * 这个布尔值确定当前sql连接是否接受无效的更新或者插入操作 即effect 0
     * 如果设置为true 一旦出现effect 0
     * 则抛出NoneffectiveUpdateExecuteException异常 并且设置threadNeedToRollback为true
     * 于是进行回滚
     */
    private static ThreadLocal<Boolean>             threadAllowNoneffectiveUpdate   = new ThreadLocal<>();

    private static void bindConn(Connection connection) {
        threadConnection.set(connection);
    }

    public static boolean setRollbackFlagTrue() {
        threadNeedToRollback.set(true);
        return true;
    }

    public static boolean setRollbackFlagFalse() {
        threadNeedToRollback.set(false);
        return false;
    }

    public static boolean setNoneffectiveUpdateFlag(boolean flag) {
        threadAllowNoneffectiveUpdate.set(flag);
        return flag;
    }

    public static Connection getThreadConnection() {

        Connection connection = threadConnection.get();

        if (connection == null) {
            try {
                connection = YouDbManager.youDruid.getCurrentDataSourceConn();
                bindConn(connection);
            } catch (SQLException | NoDataSourceInitException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

    public static Boolean getRollbackFlag() {
        Boolean needToRollBack = threadNeedToRollback.get();
        return needToRollBack == null ? setRollbackFlagFalse() : needToRollBack;
    }

    public static Boolean getNoneffectiveUpdateFlag() {
        Boolean allowNoneffectiveUpdate = threadAllowNoneffectiveUpdate.get();
        return allowNoneffectiveUpdate == null ? setNoneffectiveUpdateFlag(true) : allowNoneffectiveUpdate;
    }
    public static void removeRollbackFlag() {threadNeedToRollback.remove();}

    public static void removeThreadConnection() {threadConnection.remove();}

    public static void removeNoneffectiveUpdateFlag() {threadAllowNoneffectiveUpdate.remove();}

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
