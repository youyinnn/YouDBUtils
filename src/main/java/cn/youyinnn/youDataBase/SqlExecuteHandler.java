package cn.youyinnn.youDataBase;

import cn.youyinnn.youDataBase.druid.YouDruid;
import cn.youyinnn.youDataBase.druid.exception.NoDataSourceInitException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/19
 */
public class SqlExecuteHandler {

    private static void checkConnection(){

        ConnectionContainer instance = ConnectionContainer.getInstance();
        if (instance.getConn() == null){
            try {
                Connection conn = YouDruid.getCurrentDataSourceConn();
                instance.bindConn(conn);
            } catch (SQLException | NoDataSourceInitException e) {
                e.printStackTrace();
            }
        }
    }

    public static ResultSet executeQuery(String sql) {

        checkConnection();
        ResultSet result = null;
        Connection conn = ConnectionContainer.getInstance().getConn();
        try {
            Statement statement = conn.createStatement();
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int executeUpdate(String sql){

        checkConnection();
        int result = 0;
        Connection conn = ConnectionContainer.getInstance().getConn();
        try {
            Statement statement = conn.createStatement();
            result = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
