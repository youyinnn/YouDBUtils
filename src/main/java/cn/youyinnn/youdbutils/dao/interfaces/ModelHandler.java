package cn.youyinnn.youdbutils.dao.interfaces;

import cn.youyinnn.youdbutils.exceptions.NoneffectiveUpdateExecuteException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 提供了一系列方法在Model的层面上进行数据库操作.
 *
 * @param <T> the type parameter
 * @author youyinnn
 */
public interface ModelHandler<T> {

    /**
     * Gets list.
     *
     * @param sql the sql
     * @return the list
     */
    ArrayList<T> getList(String sql);

    /**
     * 获取这个model类的所有记录
     *
     * @param queryFieldList 为空则是select * 否则就在这指定需要查询的具体字段
     * @return 查询结果集转化出的Model类列表 list for all
     */
    ArrayList<T> getListForAll(ArrayList<String> queryFieldList);

    /**
     * 获取这个model的符合字段值条件的记录 条件之间使用separateMark连接
     *
     * @param conditionsMap  需要定位更新记录的条件字段以及条件值组成的键值对
     * @param queryFieldList 为空则是select * 否则就在这指定需要查询的具体字段
     * @param separateMark   连接条件的连词
     * @return the list where a and b
     */
    ArrayList<T> getListWhere(HashMap<String,Object> conditionsMap, ArrayList<String> queryFieldList,String separateMark);

    /**
     * 获取这个model的模糊符合字段值条件的记录 条件之间使用separateMark连接
     *
     * @param conditionsMap  需要定位更新记录的条件字段以及条件值组成的键值对
     * @param queryFieldList 为空则是select * 否则就在这指定需要查询的具体字段
     * @param separateMark   连接条件的连词
     * @return the list where like and like
     */
    ArrayList<T> getListWhereLike(HashMap<String,Object> conditionsMap, ArrayList<String> queryFieldList,String separateMark);


    /**
     * 传入Model类，保存到数据库记录中
     *
     * @param model 需要保持的Model类
     * @return the int
     * @throws NoneffectiveUpdateExecuteException the noneffective update execute exception
     */
    int saveModel(T model) throws NoneffectiveUpdateExecuteException;

    /**
     * Gets model.
     *
     * @param sql the sql
     * @return the model
     */
    T getModel(String sql);

    /**
     * Gets model.
     *
     * @param sql             the sql
     * @param conditionValues the condition values
     * @return the model
     */
    T getModel(String sql, ArrayList<String> conditionValues);

    /**
     * 获取单个Model对象
     *
     * @param conditionsMap  the conditions map
     * @param queryFieldList the query field list
     * @param separateMark   连接条件的连词
     * @return the model
     */
    T getModel(HashMap<String, Object> conditionsMap, ArrayList<String> queryFieldList,String separateMark);

    /**
     * 获取单个记录的某个值
     *
     * @param fieldName     the field name
     * @param conditionsMap the conditions map
     * @param separateMark  连接条件的连词
     * @return the model field value
     */
    Object getModelFieldValue(String fieldName,HashMap<String, Object> conditionsMap,String separateMark);

    /**
     * Update model int.
     *
     * @param newFieldValuesMap the new field values map
     * @param conditionsMap     the conditions map
     * @param separateMark      连接条件的连词
     * @return the int
     * @throws NoneffectiveUpdateExecuteException the noneffective update execute exception
     */
    int updateModel(HashMap<String, Object> newFieldValuesMap, HashMap<String, Object> conditionsMap,String separateMark) throws NoneffectiveUpdateExecuteException;

    /**
     * Delete model int.
     *
     * @param conditionsMap the conditions map
     * @param separateMark  连接条件的连词
     * @return the int
     * @throws NoneffectiveUpdateExecuteException the noneffective update execute exception
     */
    int deleteModel(HashMap<String, Object> conditionsMap,String separateMark) throws NoneffectiveUpdateExecuteException;

    /**
     * 对同一个值简单的做加法运算
     *
     * @param fieldName     the field name
     * @param b             the b
     * @param conditionsMap the conditions map
     * @return the int
     * @throws NoneffectiveUpdateExecuteException the noneffective update execute exception
     */
    int addition(String fieldName, double b, HashMap<String, Object> conditionsMap) throws NoneffectiveUpdateExecuteException;

    /**
     * 对同一个值简单的做减法运算
     *
     * @param fieldName     the field name
     * @param b             the b
     * @param conditionsMap the conditions map
     * @return the int
     * @throws NoneffectiveUpdateExecuteException the noneffective update execute exception
     */
    int subtraction(String fieldName, double b, HashMap<String, Object> conditionsMap) throws NoneffectiveUpdateExecuteException;

    /**
     * 对同一个值简单的做乘法运算
     *
     * @param fieldName     the field name
     * @param b             the b
     * @param conditionsMap the conditions map
     * @return the int
     * @throws NoneffectiveUpdateExecuteException the noneffective update execute exception
     */
    int multiplication(String fieldName, double b, HashMap<String, Object> conditionsMap) throws NoneffectiveUpdateExecuteException;

    /**
     * 对同一个值简单的做除法运算
     *
     * @param fieldName     the field name
     * @param b             the b
     * @param conditionsMap the conditions map
     * @return the int
     * @throws NoneffectiveUpdateExecuteException the noneffective update execute exception
     */
    int division(String fieldName, double b, HashMap<String, Object> conditionsMap) throws NoneffectiveUpdateExecuteException;
}
