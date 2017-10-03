package cn.youyinnn.youDataBase;

import cn.youyinnn.youDataBase.utils.SqlStringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/19
 */
public class SqlExecuteHandler<T> implements cn.youyinnn.youDataBase.interfaces.SqlExecuteHandler<T> {

    public static boolean isRollback = false;

    private static SqlExecuteHandler instance = new SqlExecuteHandler();

    private SqlExecuteHandler(){}

    public static SqlExecuteHandler getInstance() {
        return instance;
    }

    private void release(Statement statement,ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<T> statementExecuteQuery(Connection conn, String sql,Class modelClass) {
        ResultSet resultSet = null;
        Statement statement = null;
        ArrayList<T> resultModelList = null;
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
            resultModelList = ModelResultFactory.getResultModelList(resultSet, modelClass);
        } catch (SQLException e) {
            isRollback = true;
            e.printStackTrace();
        } finally {
            release(statement,resultSet);
        }
        return resultModelList;
    }

    private ArrayList<T> preparedStatementExecuteQuery(Connection conn, String sql, Class modelClass, Collection values){

        PreparedStatement ps = null;
        ResultSet resultSet = null;
        ArrayList<T> resultModelList = null;
        try {
            ps = conn.prepareStatement(sql);
            int i = 1;
            for (Object value : values) {
                ps.setObject(i++,value);
            }
            resultSet = ps.executeQuery();

            resultModelList = ModelResultFactory.getResultModelList(resultSet,modelClass);
        } catch (SQLException e) {
            isRollback = true;
            e.printStackTrace();
        } finally {
            release(ps,resultSet);
        }
        return resultModelList;
    }

    @Override
    public ArrayList<T> executeQuery(Class modelClass,String sql)  {

        return statementExecuteQuery(ConnectionContainer.getInstance().getConn(),sql,modelClass);
    }

    @Override
    public int executeUpdate(String sql)  {

        int result = 0;
        Connection conn = ConnectionContainer.getInstance().getConn();
        Statement statement = null;
        try {
            statement = conn.createStatement();
            result = statement.executeUpdate(sql);
        } catch (SQLException e) {
            isRollback = true;
            e.printStackTrace();
        } finally {
            release(statement,null);
        }

        return result;
    }

    @Override
    public ArrayList<T> getListForAll(Class modelClass, ArrayList<String> queryFieldList){

        String sql = SqlStringUtils.getSelectAllSql(modelClass.getSimpleName(),queryFieldList);

        return statementExecuteQuery(ConnectionContainer.getInstance().getConn(),sql,modelClass);
    }

    @Override
    public ArrayList<T> getListWhereAAndB(Class modelClass, HashMap<String, Object> conditionMap, ArrayList<String> queryFieldList) {

        String sql = SqlStringUtils.getWhereSql(modelClass.getSimpleName(),conditionMap.keySet(),"AND",queryFieldList);

        return preparedStatementExecuteQuery(ConnectionContainer.getInstance().getConn(),sql,modelClass,conditionMap.values());
    }

    @Override
    public ArrayList<T> getListWhereAOrB(Class modelClass, HashMap<String, Object> conditionMap, ArrayList<String> queryFieldList) {

        String sql = SqlStringUtils.getWhereSql(modelClass.getSimpleName(),conditionMap.keySet(),"OR",queryFieldList);

        return preparedStatementExecuteQuery(ConnectionContainer.getInstance().getConn(),sql,modelClass,conditionMap.values());
    }

    @Override
    public ArrayList<T> getListWhereLikeAndLike(Class modelClass, HashMap<String, Object> conditionMap, ArrayList<String> queryFieldList) {

        String sql = SqlStringUtils.getWhereLikeSql(modelClass.getSimpleName(),conditionMap,"AND",queryFieldList);

        return statementExecuteQuery(ConnectionContainer.getInstance().getConn(),sql,modelClass);
    }

    @Override
    public ArrayList<T> getListWhereLikeOrLike(Class modelClass, HashMap<String, Object> conditionMap, ArrayList<String> queryFieldList) {

        String sql = SqlStringUtils.getWhereLikeSql(modelClass.getSimpleName(),conditionMap,"OR",queryFieldList);

        return statementExecuteQuery(ConnectionContainer.getInstance().getConn(),sql,modelClass);
    }

}
