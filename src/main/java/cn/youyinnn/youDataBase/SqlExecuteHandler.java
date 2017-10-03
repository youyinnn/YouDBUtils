package cn.youyinnn.youDataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/19
 */
public class SqlExecuteHandlerImpl implements cn.youyinnn.youDataBase.interfaces.SqlExecuteHandler {

    public static boolean isRollback = false;

    private SqlExecuteHandlerImpl(){}

    private static SqlExecuteHandlerImpl instance = new SqlExecuteHandlerImpl();

    public static SqlExecuteHandlerImpl getInstance() {
        return instance;
    }

    @Override
    public ResultSet executeQuery(String sql)  {

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

    @Override
    public int executeUpdate(String sql)  {

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

    public List queryList(){

        Connection conn = ConnectionContainer.getInstance().getConn();


        return null;
    }

}
