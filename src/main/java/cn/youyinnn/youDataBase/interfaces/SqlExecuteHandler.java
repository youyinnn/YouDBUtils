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
     * @param modelClass        需要更新的model类 我们使用这个来对应数据表
     * @param newFieldValuesMap 需要更新的字段以及新值组成的键值对
     * @param conditionsMap     需要定位更新记录的条件字段以及条件值组成的键值对
     * @return 更新操作影响到的记录数
     */
    int executePreparedStatementUpdate(Class modelClass, HashMap<String, Object> newFieldValuesMap, HashMap<String, Object> conditionsMap);

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

    /**
     * 获取这个model类的所有记录
     *
     * @param modelClass     需要查询的记录对应的model类
     * @param queryFieldList 为空则是select * 否则就在这指定需要查询的具体字段
     * @return 查询结果集转化出的Model类列表
     */
    ArrayList<T> getListForAll(Class modelClass, ArrayList<String> queryFieldList);

    /**
     * 获取这个model的符合字段值条件的记录 条件之间使用and连接
     *
     * @param modelClass     需要查询的记录对应的model类
     * @param conditionsMap  需要定位更新记录的条件字段以及条件值组成的键值对
     * @param queryFieldList 为空则是select * 否则就在这指定需要查询的具体字段
     * @return the list where a and b
     */
    ArrayList<T> getListWhereAAndB(Class modelClass, HashMap<String,Object> conditionsMap, ArrayList<String> queryFieldList);

    /**
     * 获取这个model的符合字段值条件的记录 条件之间使用or连接
     *
     * @param modelClass     需要查询的记录对应的model类
     * @param conditionsMap  需要定位更新记录的条件字段以及条件值组成的键值对
     * @param queryFieldList 为空则是select * 否则就在这指定需要查询的具体字段
     * @return the list where a or b
     */
    ArrayList<T> getListWhereAOrB(Class modelClass, HashMap<String,Object> conditionsMap, ArrayList<String> queryFieldList);

    /**
     * 获取这个model的模糊符合字段值条件的记录 条件之间使用and连接
     *
     * @param modelClass     需要查询的记录对应的model类
     * @param conditionsMap  需要定位更新记录的条件字段以及条件值组成的键值对
     * @param queryFieldList 为空则是select * 否则就在这指定需要查询的具体字段
     * @return the list where like and like
     */
    ArrayList<T> getListWhereLikeAndLike(Class modelClass, HashMap<String,Object> conditionsMap, ArrayList<String> queryFieldList);

    /**
     * 获取这个model的模糊符合字段值条件的记录 条件之间使用or连接
     *
     * @param modelClass     需要查询的记录对应的model类
     * @param conditionsMap  需要定位更新记录的条件字段以及条件值组成的键值对
     * @param queryFieldList 为空则是select * 否则就在这指定需要查询的具体字段
     * @return the list where like or like
     */
    ArrayList<T> getListWhereLikeOrLike(Class modelClass, HashMap<String,Object> conditionsMap, ArrayList<String> queryFieldList);
}
