package cn.youyinnn.helloworld;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/10
 */
public class MySQLiteUtils {

    public static Connection getConn(String databaseName){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConn(String databaseName, boolean autoCommit) {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+databaseName);
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }


}
