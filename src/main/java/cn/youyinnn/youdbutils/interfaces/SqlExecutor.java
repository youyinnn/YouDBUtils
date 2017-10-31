package cn.youyinnn.youdbutils.interfaces;

import cn.youyinnn.youdbutils.exceptions.NoneffectiveUpdateExecuteException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The interface Sql execute handler.
 *
 * @author youyinnn
 */
public interface SqlExecutor {

    /**
     * 使用statement执行正常的update操作
     *
     * @param sql 一个完整的update类型的sql语句
     * @return 更新操作影响到的记录数 int
     */
    int executeStatementUpdate(String sql) throws NoneffectiveUpdateExecuteException;

    /**
     * 使用PreparedStatement执行update操作
     *
     * @param sql             一个完整的带有占位符的update类型的sql语句
     * @param newFieldValues  更新值的填充列表
     * @param conditionValues 条件值的填充列表 允许为空
     * @return 更新操作影响到的记录数 int
     */
    int executePreparedStatementUpdate(String sql, ArrayList newFieldValues, ArrayList conditionValues) throws NoneffectiveUpdateExecuteException;

    /**
     * 使用PreparedStatement执行update操作
     *
     * @param modelName         需要更新的model类名 我们使用这个来对应数据表
     * @param newFieldValuesMap 需要更新的字段以及新值组成的键值对
     * @param conditionsMap     需要定位更新记录的条件字段以及条件值组成的键值对
     * @return 更新操作影响到的记录数 int
     */
    int executePreparedStatementUpdate(String modelName, HashMap<String, Object> newFieldValuesMap, HashMap<String, Object> conditionsMap) throws NoneffectiveUpdateExecuteException;

    /**
     * 使用Statement执行insert操作
     *
     * @param sql the sql
     * @return the int
     */
    int executeStatementInsert(String sql) throws NoneffectiveUpdateExecuteException;

    /**
     * 使用PreparedStatement执行insert操作
     *
     * @param sql            the sql
     * @param newFieldValues 填充的新值列表
     * @return the int
     */
    int executePreparedStatementInsert(String sql, ArrayList newFieldValues) throws NoneffectiveUpdateExecuteException;

    /**
     * 使用PreparedStatement执行insert操作
     *
     * @param modelName         需要插入的model类名 我们使用这个来对应数据表
     * @param newFieldValuesMap 填充的新值列表
     * @return the int
     */
    int executePreparedStatementInsert(String modelName, HashMap<String, Object> newFieldValuesMap) throws NoneffectiveUpdateExecuteException;

    /**
     * 使用Statement执行delete操作
     *
     * @param sql the sql
     * @return the int
     */
    int executeStatementDelete(String sql) throws NoneffectiveUpdateExecuteException;

    /**
     * 使用PreparedStatement执行delete操作
     *
     * @param sql             the sql
     * @param conditionValues 要填充的条件值列表
     * @return the int
     */
    int executePreparedStatementDelete(String sql, ArrayList conditionValues) throws NoneffectiveUpdateExecuteException;

    /**
     * 使用PreparedStatement执行delete操作
     *
     * @param modelName     需要删除的model类名 我们使用这个来对应数据表
     * @param conditionsMap 需要定位删除记录的条件字段以及条件值组成的键值对
     * @return the int
     */
    int executePreparedStatementDelete(String modelName, HashMap<String, Object> conditionsMap) throws NoneffectiveUpdateExecuteException;

    /**
     * 使用Statement执行query操作
     *
     * @param sql 一个完整的select类型的sql语句
     * @return 查询结果集转化出的Model类列表 result set
     * @throws SQLException the sql exceptions
     */
    ResultSet executeStatementQuery(String sql) throws SQLException;

    /**
     * 使用PreparedStatement执行query操作
     *
     * @param sql             一个完整的带有占位符的select类型的sql语句
     * @param conditionValues 要填充的条件值列表
     * @return 查询结果集转化出的Model类列表 result set
     * @throws SQLException the sql exceptions
     */
    ResultSet executePreparedStatementQuery(String sql, ArrayList conditionValues) throws SQLException;

    /**
     * 使用PreparedStatement执行query操作
     *
     * @param modelName      需要查询的model类名 我们使用这个来对应数据表
     * @param queryFieldList 需要查询的字段列名
     * @param conditionMap   需要定位查询记录的条件字段以及条件值组成的键值对
     * @return the result set
     * @throws SQLException the sql exceptions
     */
    ResultSet executePreparedStatementQuery(String modelName, ArrayList<String> queryFieldList, HashMap<String,Object> conditionMap) throws SQLException;


}
