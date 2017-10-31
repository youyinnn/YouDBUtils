package cn.youyinnn.youdbutils.dao;

import cn.youyinnn.youdbutils.druid.ThreadLocalPropContainer;
import cn.youyinnn.youdbutils.exceptions.NoneffectiveUpdateExecuteException;
import cn.youyinnn.youdbutils.utils.SqlStringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/9/19
 */
public class SqlExecutor implements cn.youyinnn.youdbutils.interfaces.SqlExecutor {

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

    private int preparedStatementUpdate(Connection conn, String sql, Collection newFieldValues, Collection conditionValues) throws NoneffectiveUpdateExecuteException {
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
            ThreadLocalPropContainer.getInstance().setFlagTrue();
            e.printStackTrace();
        } finally {
            ThreadLocalPropContainer.release(null, ps,null);
        }

        if (result == 0) {
            ThreadLocalPropContainer.getInstance().setFlagTrue();
            throw new NoneffectiveUpdateExecuteException("无效的更新操作");
        }

        return result;
    }

    @Override
    public int executeStatementUpdate(String sql) throws NoneffectiveUpdateExecuteException {

        int result = 0;
        Connection conn = ThreadLocalPropContainer.getInstance().getConn();
        Statement statement = null;
        try {
            statement = conn.createStatement();
            result = statement.executeUpdate(sql);
        } catch (SQLException e) {
            ThreadLocalPropContainer.getInstance().setFlagTrue();
            e.printStackTrace();
        } finally {
            ThreadLocalPropContainer.release(null, statement,null);
        }

        if (result == 0) {
            ThreadLocalPropContainer.getInstance().setFlagTrue();
            throw new NoneffectiveUpdateExecuteException("无效的更新操作");
        }

        return result;
    }

    @Override
    public int executePreparedStatementUpdate(String modelName, HashMap<String, Object> newFieldValuesMap, HashMap<String, Object> conditionsMap) throws NoneffectiveUpdateExecuteException {

        String sql = SqlStringUtils.getUpdateSetWhereSql(modelName,newFieldValuesMap.keySet(),"AND",conditionsMap != null ? conditionsMap.keySet() : null);

        return preparedStatementUpdate(ThreadLocalPropContainer.getInstance().getConn(),sql,newFieldValuesMap.values(), conditionsMap != null ? conditionsMap.values() : null);
    }

    @Override
    public int executePreparedStatementUpdate(String sql, ArrayList newFieldValues, ArrayList conditionValues) throws NoneffectiveUpdateExecuteException {

        return preparedStatementUpdate(ThreadLocalPropContainer.getInstance().getConn(),sql,newFieldValues, conditionValues);
    }

    @Override
    public int executeStatementInsert(String sql) throws NoneffectiveUpdateExecuteException {

        return executeStatementUpdate(sql);
    }

    @Override
    public int executePreparedStatementInsert(String sql, ArrayList newFieldValues) throws NoneffectiveUpdateExecuteException {

        return preparedStatementUpdate(ThreadLocalPropContainer.getInstance().getConn(),sql,newFieldValues,null);
    }

    @Override
    public int executePreparedStatementInsert(String modelName, HashMap<String, Object> newFieldValuesMap) throws NoneffectiveUpdateExecuteException {

        String sql = SqlStringUtils.getInsertSql(modelName,newFieldValuesMap.keySet());

        return preparedStatementUpdate(ThreadLocalPropContainer.getInstance().getConn(),sql,newFieldValuesMap.values(),null);
    }

    @Override
    public int executeStatementDelete(String sql) throws NoneffectiveUpdateExecuteException {

        return executeStatementUpdate(sql);
    }

    @Override
    public int executePreparedStatementDelete(String sql, ArrayList conditionValues) throws NoneffectiveUpdateExecuteException {

        return preparedStatementUpdate(ThreadLocalPropContainer.getInstance().getConn(),sql,null,conditionValues);
    }

    @Override
    public int executePreparedStatementDelete(String modelName, HashMap<String, Object> conditionsMap) throws NoneffectiveUpdateExecuteException {

        String sql = SqlStringUtils.getDeleteSql(modelName,"AND",conditionsMap.keySet());

        return preparedStatementUpdate(ThreadLocalPropContainer.getInstance().getConn(),sql,null,conditionsMap.values());
    }

    @Override
    public ResultSet executeStatementQuery(String sql) throws SQLException {

        return statementQuery(ThreadLocalPropContainer.getInstance().getConn(),sql);
    }

    @Override
    public ResultSet executePreparedStatementQuery(String sql, ArrayList values) throws SQLException {

        return preparedStatementQuery(ThreadLocalPropContainer.getInstance().getConn(),sql,values);
    }

    @Override
    public ResultSet executePreparedStatementQuery(String modelName, ArrayList<String> queryFieldList, HashMap<String, Object> conditionMap) throws SQLException {

        String sql = SqlStringUtils.getSelectFromWhereSql(modelName,conditionMap.keySet(),"AND",queryFieldList);

        return preparedStatementQuery(ThreadLocalPropContainer.getInstance().getConn(),sql, conditionMap.values());
    }
}
