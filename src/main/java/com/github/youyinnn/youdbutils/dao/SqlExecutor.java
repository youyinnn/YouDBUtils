package com.github.youyinnn.youdbutils.dao;

import com.github.youyinnn.youdbutils.druid.ThreadLocalPropContainer;
import com.github.youyinnn.youdbutils.exceptions.NoneffectiveUpdateExecuteException;
import com.github.youyinnn.youdbutils.utils.SqlStringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * 见接口
 *
 * @author youyinnn
 *
 */
public class SqlExecutor implements com.github.youyinnn.youdbutils.dao.interfaces.SqlExecutor {

    private String dataSourceName;

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

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
            ThreadLocalPropContainer.setRollbackFlagTrue();
            e.printStackTrace();
        } finally {
            ThreadLocalPropContainer.release(null, ps,null);
        }
        if (result == 0 & !ThreadLocalPropContainer.getNoneffectiveUpdateFlag()) {
            ThreadLocalPropContainer.setRollbackFlagTrue();
            throw new NoneffectiveUpdateExecuteException("不允许存在无效的更新操作");
        }
        return result;
    }

    @Override
    public int executeStatementUpdate(String sql) throws NoneffectiveUpdateExecuteException {
        int result = 0;
        Connection conn = ThreadLocalPropContainer.getThreadConnection(dataSourceName);
        Statement statement = null;
        try {
            statement = conn.createStatement();
            result = statement.executeUpdate(sql);
        } catch (SQLException e) {
            ThreadLocalPropContainer.setRollbackFlagTrue();
            e.printStackTrace();
        } finally {
            ThreadLocalPropContainer.release(null, statement,null);
        }
        if (result == 0 & !ThreadLocalPropContainer.getNoneffectiveUpdateFlag()) {
            ThreadLocalPropContainer.setRollbackFlagTrue();
            throw new NoneffectiveUpdateExecuteException("不允许存在无效的更新操作");
        }
        return result;
    }

    @Override
    public int executePreparedStatementUpdate(String tableName, HashMap<String, Object> newFieldValuesMap, HashMap<String, Object> conditionsMap,String separateMark) throws NoneffectiveUpdateExecuteException {
        String sql = SqlStringUtils.getUpdateSetWhereSql(tableName,newFieldValuesMap.keySet(),separateMark,conditionsMap != null ? conditionsMap.keySet() : null);
        return preparedStatementUpdate(ThreadLocalPropContainer.getThreadConnection(dataSourceName),sql,newFieldValuesMap.values(), conditionsMap != null ? conditionsMap.values() : null);
    }

    @Override
    public int executePreparedStatementUpdate(String sql, ArrayList newFieldValues, ArrayList conditionValues) throws NoneffectiveUpdateExecuteException {
        return preparedStatementUpdate(ThreadLocalPropContainer.getThreadConnection(dataSourceName),sql,newFieldValues, conditionValues);
    }

    @Override
    public int executeStatementInsert(String sql) throws NoneffectiveUpdateExecuteException {
        return executeStatementUpdate(sql);
    }

    @Override
    public int executePreparedStatementInsert(String sql, ArrayList newFieldValues) throws NoneffectiveUpdateExecuteException {
        return preparedStatementUpdate(ThreadLocalPropContainer.getThreadConnection(dataSourceName),sql,newFieldValues,null);
    }

    @Override
    public int executePreparedStatementInsert(String tableName, HashMap<String, Object> newFieldValuesMap) throws NoneffectiveUpdateExecuteException {
        String sql = SqlStringUtils.getInsertSql(tableName,newFieldValuesMap.keySet());
        return preparedStatementUpdate(ThreadLocalPropContainer.getThreadConnection(dataSourceName),sql,newFieldValuesMap.values(),null);
    }

    @Override
    public int executeStatementDelete(String sql) throws NoneffectiveUpdateExecuteException {
        return executeStatementUpdate(sql);
    }

    @Override
    public int executePreparedStatementDelete(String sql, ArrayList conditionValues) throws NoneffectiveUpdateExecuteException {
        return preparedStatementUpdate(ThreadLocalPropContainer.getThreadConnection(dataSourceName),sql,null,conditionValues);
    }

    @Override
    public int executePreparedStatementDelete(String tableName, HashMap<String, Object> conditionsMap,String separateMark) throws NoneffectiveUpdateExecuteException {
        String sql = SqlStringUtils.getDeleteSql(tableName,separateMark,conditionsMap.keySet());
        return preparedStatementUpdate(ThreadLocalPropContainer.getThreadConnection(dataSourceName),sql,null,conditionsMap.values());
    }

    @Override
    public ResultSet executeStatementQuery(String sql) throws SQLException {
        return statementQuery(ThreadLocalPropContainer.getThreadConnection(dataSourceName),sql);
    }

    @Override
    public ResultSet executePreparedStatementQuery(String sql, ArrayList values) throws SQLException {
        return preparedStatementQuery(ThreadLocalPropContainer.getThreadConnection(dataSourceName),sql,values);
    }

    @Override
    public ResultSet executePreparedStatementQuery(String tableName, ArrayList<String> queryFieldList, HashMap<String, Object> conditionMap,String separateMark) throws SQLException {
        String sql = SqlStringUtils.getSelectFromWhereSql(tableName,conditionMap.keySet(),separateMark,queryFieldList);
        return preparedStatementQuery(ThreadLocalPropContainer.getThreadConnection(dataSourceName),sql, conditionMap.values());
    }
}
