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

    public static ResultSet executeQuery(String sql) throws SQLException {

        ResultSet result = null;
        Connection conn = ConnectionContainer.getInstance().getConn();
        Statement statement = conn.createStatement();
        result = statement.executeQuery(sql);
        return result;
    }

    public static int executeUpdate(String sql) throws SQLException {

        int result = 0;
        Connection conn = ConnectionContainer.getInstance().getConn();
        Statement statement = conn.createStatement();
        result = statement.executeUpdate(sql);

        return result;
    }

}
