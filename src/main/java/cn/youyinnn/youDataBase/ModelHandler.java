package cn.youyinnn.youDataBase;

import cn.youyinnn.youDataBase.utils.SqlStringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    private ArrayList<T> getStatementResultModelList(Class<T> modelClass,String sql){
        ResultSet resultSet = null;
        ArrayList<T> resultModelList = null;
        Statement statement = null;
        try {
            resultSet = sqlExecuteHandler.executeStatementQuery(sql);
            statement = resultSet.getStatement();
            resultModelList = modelResultFactory.getResultModelList(resultSet, modelClass);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionContainer.release(statement,resultSet);
        }
        return resultModelList;
    }

    public ArrayList<T> getListForAll(Class<T> modelClass, ArrayList<String> queryFieldList){

        String sql = SqlStringUtils.getSelectAllSql(modelClass.getSimpleName(),queryFieldList);

        return getStatementResultModelList(modelClass,sql);

    }

    public ArrayList<T> getListWhereAAndB(Class<T> modelClass, HashMap<String, Object> conditionsMap, ArrayList<String> queryFieldList) {

        ResultSet resultSet = null;
        ArrayList<T> resultModelList = null;
        Statement statement = null;
        try {
            resultSet = sqlExecuteHandler.executePreparedStatementQuery(modelClass.getSimpleName(),queryFieldList,conditionsMap);
            statement = resultSet.getStatement();
            resultModelList = modelResultFactory.getResultModelList(resultSet, modelClass);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionContainer.release(statement,resultSet);
        }

        return resultModelList;
    }

    public ArrayList<T> getListWhereAOrB(Class<T> modelClass, HashMap<String, Object> conditionsMap, ArrayList<String> queryFieldList) {

        String sql = SqlStringUtils.getSelectFromWhereSql(modelClass.getSimpleName(),conditionsMap.keySet(),"OR",queryFieldList);

        ResultSet resultSet = null;
        ArrayList<T> resultModelList = null;
        Statement statement = null;
        try {
            resultSet = sqlExecuteHandler.executePreparedStatementQuery(sql, new ArrayList<>(conditionsMap.values()));
            statement = resultSet.getStatement();
            resultModelList = modelResultFactory.getResultModelList(resultSet, modelClass);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionContainer.release(statement,resultSet);
        }

        return resultModelList;
    }

    public ArrayList<T> getListWhereLikeAndLike(Class<T> modelClass, HashMap<String, Object> conditionsMap, ArrayList<String> queryFieldList) {

        String sql = SqlStringUtils.getSelectFromWhereLikeSql(modelClass.getSimpleName(),conditionsMap,"AND",queryFieldList);

        return getStatementResultModelList(modelClass,sql);
    }

    public ArrayList<T> getListWhereLikeOrLike(Class<T> modelClass, HashMap<String, Object> conditionsMap, ArrayList<String> queryFieldList) {

        String sql = SqlStringUtils.getSelectFromWhereLikeSql(modelClass.getSimpleName(),conditionsMap,"OR",queryFieldList);

        return getStatementResultModelList(modelClass,sql);
    }

}
