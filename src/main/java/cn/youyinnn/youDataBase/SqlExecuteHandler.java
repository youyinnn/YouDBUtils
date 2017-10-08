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

    private ResultSet statementQuery(Connection conn, String sql) throws SQLException {
        ResultSet resultSet;
        Statement statement;

        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);

        return resultSet;
    }

    private ResultSet preparedStatementQuery(Connection conn, String sql, Collection conditionValues) throws SQLException {

        PreparedStatement ps;
        ResultSet resultSet;

        ps = conn.prepareStatement(sql);
        int i = 1;
        for (Object value : conditionValues) {
            ps.setObject(i++,value);
        }
        resultSet = ps.executeQuery();

        return resultSet;
    }

    private int preparedStatementUpdate(Connection conn, String sql, Collection newFieldValues, Collection conditionValues){
        int result = 0;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            int i = 1;
            if (newFieldValues != null) {
                for (Object newFieldValue : newFieldValues) {
                    ps.setObject(i++,newFieldValue);
                }
            }
            if (conditionValues != null) {
                for (Object conditionValue : conditionValues) {
                    ps.setObject(i++,conditionValue);
                }
            }
            result = ps.executeUpdate();
        } catch (SQLException e) {
            isRollback = true;
            e.printStackTrace();
        } finally {
            ConnectionContainer.release(ps,null);
        }
        return result;
    }

    @Override
    public int executeStatementUpdate(String sql)  {

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
            ConnectionContainer.release(statement,null);
        }

        return result;
    }

    @Override
    public int executePreparedStatementUpdate(String modelName, HashMap<String, Object> newFieldValuesMap, HashMap<String, Object> conditionsMap) {

        String sql = SqlStringUtils.getUpdateSetWhereSql(modelName,newFieldValuesMap.keySet(),"AND",conditionsMap != null ? conditionsMap.keySet() : null);

        return preparedStatementUpdate(ConnectionContainer.getInstance().getConn(),sql,newFieldValuesMap.values(), conditionsMap != null ? conditionsMap.values() : null);
    }

    @Override
    public int executePreparedStatementUpdate(String sql, ArrayList newFieldValues, ArrayList conditionValues) {

        return preparedStatementUpdate(ConnectionContainer.getInstance().getConn(),sql,newFieldValues, conditionValues);
    }

    @Override
    public int executeStatementInsert(String sql) {

        return executeStatementUpdate(sql);
    }

    @Override
    public int executePreparedStatementInsert(String sql, ArrayList newFieldValues) {

        return preparedStatementUpdate(ConnectionContainer.getInstance().getConn(),sql,newFieldValues,null);
    }

    @Override
    public int executePreparedStatementInsert(String modelName, HashMap<String, Object> newFieldValuesMap) {

        String sql = SqlStringUtils.getInsertSql(modelName,newFieldValuesMap.keySet());

        return preparedStatementUpdate(ConnectionContainer.getInstance().getConn(),sql,newFieldValuesMap.values(),null);
    }

    @Override
    public int executeStatementDelete(String sql) {

        return executeStatementUpdate(sql);
    }

    @Override
    public int executePreparedStatementDelete(String sql, ArrayList conditionValues) {

        return preparedStatementUpdate(ConnectionContainer.getInstance().getConn(),sql,null,conditionValues);
    }

    @Override
    public int executePreparedStatementDelete(String modelName, HashMap<String, Object> conditionsMap) {

        String sql = SqlStringUtils.getDeleteSql(modelName,"AND",conditionsMap.keySet());

        return preparedStatementUpdate(ConnectionContainer.getInstance().getConn(),sql,null,conditionsMap.values());
    }

    @Override
    public ResultSet executeStatementQuery(String sql) throws SQLException {

        return statementQuery(ConnectionContainer.getInstance().getConn(),sql);
    }

    @Override
    public ResultSet executePreparedStatementQuery(String sql, ArrayList values) throws SQLException {

        return preparedStatementQuery(ConnectionContainer.getInstance().getConn(),sql,values);
    }

    @Override
    public ResultSet executePreparedStatementQuery(String modelName, ArrayList<String> queryFieldList, HashMap<String, Object> conditionMap) throws SQLException {

        String sql = SqlStringUtils.getSelectFromWhereSql(modelName,conditionMap.keySet(),"AND",queryFieldList);

        return preparedStatementQuery(ConnectionContainer.getInstance().getConn(),sql, conditionMap.values());
    }
}
