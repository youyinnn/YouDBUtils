package cn.youyinnn.youDataBase;

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

    public static boolean isRollback = false;

    public static ResultSet executeQuery(String sql)  {

        ResultSet result = null;
        Connection conn = ConnectionContainer.getInstance().getConn();
        try {
            Statement statement = conn.createStatement();
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            isRollback = true;
            e.printStackTrace();
        }
        return result;
    }

    public static int executeUpdate(String sql)  {

        int result = 0;
        Connection conn = ConnectionContainer.getInstance().getConn();
        try {
            Statement statement = conn.createStatement();
            result = statement.executeUpdate(sql);
        } catch (SQLException e) {
            isRollback = true;
            e.printStackTrace();
        }

        return result;
    }

}
