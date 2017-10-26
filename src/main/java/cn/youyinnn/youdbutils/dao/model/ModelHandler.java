package cn.youyinnn.youdbutils.dao.model;

import cn.youyinnn.youdbutils.dao.SqlExecutor;
import cn.youyinnn.youdbutils.druid.ThreadLocalPropContainer;
import cn.youyinnn.youdbutils.utils.ReflectionUtils;
import cn.youyinnn.youdbutils.utils.SqlStringUtils;
import cn.youyinnn.youdbutils.utils.YouCollectionsUtils;

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
public class ModelHandler<T> extends SqlExecutor implements cn.youyinnn.youdbutils.interfaces.ModelHandler<T>{

    private ModelResultFactory<T> modelResultFactory;

    private Class<T> modelClass;

    private String modelName;

    public ModelHandler(Class<T> modelClass) {
        this.modelResultFactory = new ModelResultFactory<>(modelClass);
        this.modelClass = modelClass;
        this.modelName = modelClass.getSimpleName();
    }

    public Class<T> getModelClass() {
        return modelClass;
    }

    public ModelResultFactory<T> getModelResultFactory() {
        return modelResultFactory;
    }

    private ArrayList<T> getStatementResultModelList(String sql){
        ResultSet resultSet = null;
        ArrayList<T> resultModelList = null;
        Statement statement = null;
        try {
            resultSet = executeStatementQuery(sql);
            statement = resultSet.getStatement();
            resultModelList = modelResultFactory.getResultModelList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ThreadLocalPropContainer.release(resultSet,statement,null);
        }
        return resultModelList;
    }

    @Override
    public ArrayList<T> getList(String sql) {

        return getStatementResultModelList(sql);
    }

    @Override
    public ArrayList<T> getListForAll(ArrayList<String> queryFieldList){

        queryFieldList = MappingHandler.mappingHandle(modelName,queryFieldList);

        String sql = SqlStringUtils.getSelectAllSql(modelName,queryFieldList);

        return getStatementResultModelList(sql);

    }

    @Override
    public ArrayList<T> getListWhereAAndB(HashMap<String, Object> conditionsMap, ArrayList<String> queryFieldList) {

        queryFieldList = MappingHandler.mappingHandle(modelName,queryFieldList);
        conditionsMap = MappingHandler.mappingHandle(modelName,conditionsMap);

        ResultSet resultSet = null;
        ArrayList<T> resultModelList = null;
        Statement statement = null;
        try {
            resultSet = executePreparedStatementQuery(modelName,queryFieldList,conditionsMap);
            statement = resultSet.getStatement();
            resultModelList = modelResultFactory.getResultModelList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ThreadLocalPropContainer.release(resultSet,statement,null);
        }

        return resultModelList;
    }

    @Override
    public ArrayList<T> getListWhereAOrB(HashMap<String, Object> conditionsMap, ArrayList<String> queryFieldList) {

        queryFieldList = MappingHandler.mappingHandle(modelName,queryFieldList);
        conditionsMap = MappingHandler.mappingHandle(modelName,conditionsMap);

        String sql = SqlStringUtils.getSelectFromWhereSql(modelName,conditionsMap.keySet(),"OR",queryFieldList);

        ResultSet resultSet = null;
        ArrayList<T> resultModelList = null;
        Statement statement = null;
        try {
            resultSet = executePreparedStatementQuery(sql, new ArrayList<>(conditionsMap.values()));
            statement = resultSet.getStatement();
            resultModelList = modelResultFactory.getResultModelList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ThreadLocalPropContainer.release(resultSet,statement,null);
        }

        return resultModelList;
    }

    @Override
    public ArrayList<T> getListWhereLikeAndLike(HashMap<String, Object> conditionsMap, ArrayList<String> queryFieldList) {

        queryFieldList = MappingHandler.mappingHandle(modelName,queryFieldList);
        conditionsMap = MappingHandler.mappingHandle(modelName,conditionsMap);

        String sql = SqlStringUtils.getSelectFromWhereLikeSql(modelName,conditionsMap,"AND",queryFieldList);

        return getStatementResultModelList(sql);
    }

    @Override
    public ArrayList<T> getListWhereLikeOrLike(HashMap<String, Object> conditionsMap, ArrayList<String> queryFieldList) {

        queryFieldList = MappingHandler.mappingHandle(modelName,queryFieldList);
        conditionsMap = MappingHandler.mappingHandle(modelName,conditionsMap);

        String sql = SqlStringUtils.getSelectFromWhereLikeSql(modelName,conditionsMap,"OR",queryFieldList);

        return getStatementResultModelList(sql);
    }

    @Override
    public int saveModel(T model) {

        Class<?> aClass = model.getClass();

        HashMap<String, Object> newFieldValuesMap = new HashMap<>(10);

        for (String field : ModelTableMessage.getModelFieldList(aClass.getSimpleName())) {
            Object fieldValue = ReflectionUtils.getFieldValue(model, field);
            newFieldValuesMap.put(field,fieldValue);
        }

        newFieldValuesMap = MappingHandler.mappingHandle(modelName,newFieldValuesMap);

        return executePreparedStatementInsert(aClass.getSimpleName(), newFieldValuesMap);
    }

    @Override
    public int updateModel(HashMap<String, Object> newFieldValuesMap, HashMap<String, Object> conditionsMap) {

        newFieldValuesMap = MappingHandler.mappingHandle(modelName,newFieldValuesMap);
        conditionsMap = MappingHandler.mappingHandle(modelName,conditionsMap);

        return executePreparedStatementUpdate(modelName,newFieldValuesMap,conditionsMap);
    }

    @Override
    public int deleteModel(HashMap<String, Object> conditionsMap) {

        conditionsMap = MappingHandler.mappingHandle(modelName,conditionsMap);

        return executePreparedStatementDelete(modelName,conditionsMap);
    }

    @Override
    public int addition(String modelField, double b, HashMap<String, Object> conditionsMap) {

        return basicArithmetic(modelField,b,conditionsMap,"+");
    }

    @Override
    public int subtraction(String modelField, double b, HashMap<String, Object> conditionsMap) {
        return basicArithmetic(modelField,b,conditionsMap, "-");
    }

    @Override
    public int multiplication(String modelField, double b, HashMap<String, Object> conditionsMap) {
        return basicArithmetic(modelField,b,conditionsMap, "*");
    }

    @Override
    public int division(String modelField, double b, HashMap<String, Object> conditionsMap) {
        return basicArithmetic(modelField,b,conditionsMap, "/");
    }

    private int basicArithmetic(String modelField, double b, HashMap<String, Object> conditionsMap, String op) {

        String tableField = MappingHandler.mappingHandle(modelName, modelField);

        StringBuffer sql = new StringBuffer("UPDATE ")
                .append(modelName)
                .append(" SET ")
                .append(tableField)
                .append(" = ")
                .append(tableField)
                .append(op)
                .append(" ?")
                .append(SqlStringUtils.getWhereSubStr(conditionsMap.keySet(),"AND"));

        ArrayList conditionValues = new ArrayList();

        conditionValues.addAll(conditionsMap.values());

        return executePreparedStatementUpdate(sql.toString(), YouCollectionsUtils.getYouArrayList(b+""), conditionValues);
    }
}
