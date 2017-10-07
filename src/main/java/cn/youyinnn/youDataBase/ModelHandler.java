package cn.youyinnn.youDataBase;

import cn.youyinnn.youDataBase.utils.SqlStringUtils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/10/7
 */
public class ModelHandler<T> implements cn.youyinnn.youDataBase.interfaces.ModelHandler<T>{

    private SqlExecuteHandler<T> sqlExecuteHandler = new SqlExecuteHandler<>();

    private ModelResultFactory<T> modelResultFactory = new ModelResultFactory<>();

    public ArrayList<T> getListForAll(Class modelClass, ArrayList<String> queryFieldList){

        String sql = SqlStringUtils.getSelectAllSql(modelClass.getSimpleName(),queryFieldList);

        ResultSet resultSet = sqlExecuteHandler.executeStatementQuery(modelClass, sql);

        return modelResultFactory.getResultModelList(resultSet,modelClass);
    }

    public ArrayList<T> getListWhereAAndB(Class modelClass, HashMap<String, Object> conditionsMap, ArrayList<String> queryFieldList) {

        String sql = SqlStringUtils.getSelectFromWhereSql(modelClass.getSimpleName(),conditionsMap.keySet(),"AND",queryFieldList);

        ResultSet resultSet = sqlExecuteHandler.executePreparedStatementQuery(modelClass, sql, new ArrayList<>(conditionsMap.values()));

        return modelResultFactory.getResultModelList(resultSet,modelClass);
    }

    public ArrayList<T> getListWhereAOrB(Class modelClass, HashMap<String, Object> conditionsMap, ArrayList<String> queryFieldList) {

        String sql = SqlStringUtils.getSelectFromWhereSql(modelClass.getSimpleName(),conditionsMap.keySet(),"OR",queryFieldList);

        ResultSet resultSet = sqlExecuteHandler.executePreparedStatementQuery(modelClass, sql, new ArrayList<>(conditionsMap.values()));

        return modelResultFactory.getResultModelList(resultSet,modelClass);
    }

    public ArrayList<T> getListWhereLikeAndLike(Class modelClass, HashMap<String, Object> conditionsMap, ArrayList<String> queryFieldList) {

        String sql = SqlStringUtils.getSelectFromWhereLikeSql(modelClass.getSimpleName(),conditionsMap,"AND",queryFieldList);

        ResultSet resultSet = sqlExecuteHandler.executeStatementQuery(modelClass, sql);

        return modelResultFactory.getResultModelList(resultSet,modelClass);
    }

    public ArrayList<T> getListWhereLikeOrLike(Class modelClass, HashMap<String, Object> conditionsMap, ArrayList<String> queryFieldList) {

        String sql = SqlStringUtils.getSelectFromWhereLikeSql(modelClass.getSimpleName(),conditionsMap,"OR",queryFieldList);

        ResultSet resultSet = sqlExecuteHandler.executeStatementQuery(modelClass, sql);

        return modelResultFactory.getResultModelList(resultSet,modelClass);
    }

}
