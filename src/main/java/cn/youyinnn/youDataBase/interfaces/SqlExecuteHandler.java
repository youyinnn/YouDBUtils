package cn.youyinnn.youDataBase.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The interface Sql execute handler.
 *
 * @param <T> the type parameter
 */
public interface SqlExecuteHandler<T> {

    /**
     * 使用statement执行正常的update操作
     *
     * @param sql 一个完整的update类型的sql语句
     * @return 更新操作影响到的记录数
     */
    int executeStatementUpdate(String sql);

    /**
     * 使用PreparedStatement执行update操作
     *
     * @param sql             一个完整的带有占位符的update类型的sql语句
     * @param newFieldValues  更新值的填充列表
     * @param conditionValues 条件值的填充列表 允许为空
     * @return 更新操作影响到的记录数
     */
    int executePreparedStatementUpdate(String sql, ArrayList newFieldValues, ArrayList conditionValues);

    /**
     * 使用PreparedStatement执行update操作
     *
     * @param modelClass        需要更新的model类 我们使用这个来对应数据表
     * @param newFieldValuesMap 需要更新的字段以及新值组成的键值对
     * @param conditionsMap     需要定位更新记录的条件字段以及条件值组成的键值对
     * @return 更新操作影响到的记录数
     */
    int executePreparedStatementUpdate(Class modelClass, HashMap<String, Object> newFieldValuesMap, HashMap<String, Object> conditionsMap);

    int executeStatementInsert(String sql);

    int executePreparedStatementInsert(String sql, ArrayList newFieldValues);

    int executePreparedStatementInsert(Class modelClass, HashMap<String, Object> newFieldValuesMap);

    int executeStatementDelete(String sql);

    int executePreparedStatementDelete(String sql, ArrayList conditionValues);

    /**
     * 使用Statement执行query操作
     *
     * @param modelClass 需要查询的记录对应的model类
     * @param sql        一个完整的select类型的sql语句
     * @return 查询结果集转化出的Model类列表
     */
    ArrayList<T> executeStatementQuery(Class modelClass,String sql);

    /**
     * 使用PreparedStatement执行query操作
     *
     * @param modelClass 需要查询的记录对应的model类
     * @param sql        一个完整的带有占位符的select类型的sql语句
     * @param values     占位符的填充列表
     * @return 查询结果集转化出的Model类列表
     */
    ArrayList<T> executePreparedStatementQuery(Class modelClass,String sql, ArrayList values);


}
