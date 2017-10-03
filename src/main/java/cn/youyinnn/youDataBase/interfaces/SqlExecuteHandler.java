package cn.youyinnn.youDataBase.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

public interface SqlExecuteHandler<T> {

    int executeUpdate(String sql);

    ArrayList<T> executeQuery(Class modelClass,String sql);

    ArrayList<T> getListForAll(Class modelClass, ArrayList<String> queryFieldList);

    ArrayList<T> getListWhereAAndB(Class modelClass, HashMap<String,Object> conditionMap, ArrayList<String> queryFieldList);

    ArrayList<T> getListWhereAOrB(Class modelClass, HashMap<String,Object> conditionMap, ArrayList<String> queryFieldList);

    ArrayList<T> getListWhereLikeAndLike(Class modelClass, HashMap<String,Object> conditionMap, ArrayList<String> queryFieldList);

    ArrayList<T> getListWhereLikeOrLike(Class modelClass, HashMap<String,Object> conditionMap, ArrayList<String> queryFieldList);
}
