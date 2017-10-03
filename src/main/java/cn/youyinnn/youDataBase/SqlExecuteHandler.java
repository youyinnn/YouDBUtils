package cn.youyinnn.youDataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/19
 */
public class SqlExecuteHandler<T> implements cn.youyinnn.youDataBase.interfaces.SqlExecuteHandler<T> {

    public static boolean isRollback = false;

    private SqlExecuteHandler(){}

    private static SqlExecuteHandler instance = new SqlExecuteHandler();

    public static SqlExecuteHandler getInstance() {
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

    @Override
    public List<T> queryList(Class modelClass){

        Connection conn = ConnectionContainer.getInstance().getConn();

        String sql = "SELECT * FROM "+modelClass.getSimpleName();

        ResultSet result;

        ArrayList resultModelList = null;

        try {
            Statement statement = conn.createStatement();

            result = statement.executeQuery(sql);

            resultModelList = ModelResultFactory.getResultModelList(result, modelClass);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultModelList;
    }

}
