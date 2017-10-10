package cn.youyinnn.youDBUtils.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

public interface ModelHandler<T> {

    ArrayList<T> getList(Class<T> modelClass,String sql);

    /**
     * 获取这个model类的所有记录
     *
     * @param modelClass     需要查询的记录对应的model类
     * @param queryFieldList 为空则是select * 否则就在这指定需要查询的具体字段
     * @return 查询结果集转化出的Model类列表
     */
    ArrayList<T> getListForAll(Class<T> modelClass, ArrayList<String> queryFieldList);

    /**
     * 获取这个model的符合字段值条件的记录 条件之间使用and连接
     *
     * @param modelClass     需要查询的记录对应的model类
     * @param conditionsMap  需要定位更新记录的条件字段以及条件值组成的键值对
     * @param queryFieldList 为空则是select * 否则就在这指定需要查询的具体字段
     * @return the list where a and b
     */
    ArrayList<T> getListWhereAAndB(Class<T> modelClass, HashMap<String,Object> conditionsMap, ArrayList<String> queryFieldList);

    /**
     * 获取这个model的符合字段值条件的记录 条件之间使用or连接
     *
     * @param modelClass     需要查询的记录对应的model类
     * @param conditionsMap  需要定位更新记录的条件字段以及条件值组成的键值对
     * @param queryFieldList 为空则是select * 否则就在这指定需要查询的具体字段
     * @return the list where a or b
     */
    ArrayList<T> getListWhereAOrB(Class<T> modelClass, HashMap<String,Object> conditionsMap, ArrayList<String> queryFieldList);

    /**
     * 获取这个model的模糊符合字段值条件的记录 条件之间使用and连接
     *
     * @param modelClass     需要查询的记录对应的model类
     * @param conditionsMap  需要定位更新记录的条件字段以及条件值组成的键值对
     * @param queryFieldList 为空则是select * 否则就在这指定需要查询的具体字段
     * @return the list where like and like
     */
    ArrayList<T> getListWhereLikeAndLike(Class<T> modelClass, HashMap<String,Object> conditionsMap, ArrayList<String> queryFieldList);

    /**
     * 获取这个model的模糊符合字段值条件的记录 条件之间使用or连接
     *
     * @param modelClass     需要查询的记录对应的model类
     * @param conditionsMap  需要定位更新记录的条件字段以及条件值组成的键值对
     * @param queryFieldList 为空则是select * 否则就在这指定需要查询的具体字段
     * @return the list where like or like
     */
    ArrayList<T> getListWhereLikeOrLike(Class<T> modelClass, HashMap<String,Object> conditionsMap, ArrayList<String> queryFieldList);

    int saveModel(T model);
}
