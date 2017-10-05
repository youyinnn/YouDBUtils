package cn.youyinnn.youDataBase.interfaces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public interface SqlExecuteHandler<T> {

    int executeStatementUpdate(String sql);

    int executePreparedStatementUpdate(Class modelClass, HashMap<String, Object> newFieldValuesMap, HashMap<String, Object> conditionsMap);

    int executePreparedStatementUpdate(String sql, Collection newFieldValues, Collection conditionValues);

    ArrayList<T> executeStatementQuery(Class modelClass,String sql);

    ArrayList<T> executePreparedStatementQuery(Class modelClass,String sql, ArrayList values);

    ArrayList<T> getListForAll(Class modelClass, ArrayList<String> queryFieldList);

    ArrayList<T> getListWhereAAndB(Class modelClass, HashMap<String,Object> conditionsMap, ArrayList<String> queryFieldList);

    ArrayList<T> getListWhereAOrB(Class modelClass, HashMap<String,Object> conditionsMap, ArrayList<String> queryFieldList);

    ArrayList<T> getListWhereLikeAndLike(Class modelClass, HashMap<String,Object> conditionsMap, ArrayList<String> queryFieldList);

    ArrayList<T> getListWhereLikeOrLike(Class modelClass, HashMap<String,Object> conditionsMap, ArrayList<String> queryFieldList);
}
