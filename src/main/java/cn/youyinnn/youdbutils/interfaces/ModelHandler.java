package cn.youyinnn.youdbutils.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The interface Model handler.
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
     * 获取这个model的符合字段值条件的记录 条件之间使用and连接
     *
     * @param conditionsMap  需要定位更新记录的条件字段以及条件值组成的键值对
     * @param queryFieldList 为空则是select * 否则就在这指定需要查询的具体字段
     * @return the list where a and b
     */
    ArrayList<T> getListWhereAAndB(HashMap<String,Object> conditionsMap, ArrayList<String> queryFieldList);

    /**
     * 获取这个model的符合字段值条件的记录 条件之间使用or连接
     *
     * @param conditionsMap  需要定位更新记录的条件字段以及条件值组成的键值对
     * @param queryFieldList 为空则是select * 否则就在这指定需要查询的具体字段
     * @return the list where a or b
     */
    ArrayList<T> getListWhereAOrB(HashMap<String,Object> conditionsMap, ArrayList<String> queryFieldList);

    /**
     * 获取这个model的模糊符合字段值条件的记录 条件之间使用and连接
     *
     * @param conditionsMap  需要定位更新记录的条件字段以及条件值组成的键值对
     * @param queryFieldList 为空则是select * 否则就在这指定需要查询的具体字段
     * @return the list where like and like
     */
    ArrayList<T> getListWhereLikeAndLike(HashMap<String,Object> conditionsMap, ArrayList<String> queryFieldList);

    /**
     * 获取这个model的模糊符合字段值条件的记录 条件之间使用or连接
     *
     * @param conditionsMap  需要定位更新记录的条件字段以及条件值组成的键值对
     * @param queryFieldList 为空则是select * 否则就在这指定需要查询的具体字段
     * @return the list where like or like
     */
    ArrayList<T> getListWhereLikeOrLike(HashMap<String,Object> conditionsMap, ArrayList<String> queryFieldList);

    /**
     * 传入Model类，保存到数据库记录中
     *
     * @param model 需要保持的Model类
     * @return the int
     */
    int saveModel(T model);

    /**
     * Update model int.
     *
     * @param newFieldValuesMap the new field values map
     * @param conditionsMap     the conditions map
     * @return the int
     */
    int updateModel(HashMap<String, Object> newFieldValuesMap, HashMap<String, Object> conditionsMap);

    /**
     * Delete model int.
     *
     * @param conditionsMap the conditions map
     * @return the int
     */
    int deleteModel(HashMap<String, Object> conditionsMap);
}
