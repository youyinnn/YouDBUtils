package com.github.youyinnn.youdbutils.dao.model;

import com.github.youyinnn.youdbutils.dao.SqlExecutor;
import com.github.youyinnn.youdbutils.druid.ThreadLocalPropContainer;
import com.github.youyinnn.youdbutils.exceptions.NoneffectiveUpdateExecuteException;
import com.github.youyinnn.youwebutils.third.ReflectionUtils;
import com.github.youyinnn.youdbutils.utils.SqlStringUtils;
import com.github.youyinnn.youwebutils.third.YouCollectionsUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 见接口注释
 *
 * @author youyinnn
 */
public class ModelHandler<T> extends SqlExecutor implements com.github.youyinnn.youdbutils.dao.interfaces.ModelHandler<T> {

    private ModelResultFactory<T> modelResultFactory;

    private String modelName;

    private String tableName;

    public ModelHandler(Class<T> modelClass, String dataSourceName) {
        this.modelResultFactory = new ModelResultFactory<>(modelClass);
        this.modelName = modelClass.getSimpleName();
        this.tableName = ModelTableMessage.getTableName(modelName);
        super.setDataSourceName(dataSourceName);
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
        try {
            queryFieldList = MappingHandler.mappingHandle(modelName,queryFieldList);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        String sql = SqlStringUtils.getSelectAllSql(tableName,queryFieldList);
        return getStatementResultModelList(sql);
    }

    @Override
    public ArrayList<T> getListWhere(HashMap<String, Object> conditionsMap, ArrayList<String> queryFieldList,String separateMark) {
        ResultSet resultSet = null;
        ArrayList<T> resultModelList = null;
        Statement statement = null;
        try {
            queryFieldList = MappingHandler.mappingHandle(modelName,queryFieldList);
            conditionsMap = MappingHandler.mappingHandle(modelName,conditionsMap);
            resultSet = executePreparedStatementQuery(tableName,queryFieldList,conditionsMap,separateMark);
            statement = resultSet.getStatement();
            resultModelList = modelResultFactory.getResultModelList(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ThreadLocalPropContainer.release(resultSet,statement,null);
        }
        return resultModelList;
    }

    @Override
    public ArrayList<T> getListWhereLike(HashMap<String, Object> conditionsMap, ArrayList<String> queryFieldList,String separateMark) {
        try {
            queryFieldList = MappingHandler.mappingHandle(modelName,queryFieldList);
            conditionsMap = MappingHandler.mappingHandle(modelName,conditionsMap);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        String sql = SqlStringUtils.getSelectFromWhereLikeSql(tableName,conditionsMap,separateMark,queryFieldList);
        return getStatementResultModelList(sql);
    }

    @Override
    public int saveModel(T model) throws NoneffectiveUpdateExecuteException {
        HashMap<String, Object> newFieldValuesMap = new HashMap<>(10);
        for (String field : ModelTableMessage.getModelFieldList(modelName)) {
            Object fieldValue = ReflectionUtils.getFieldValue(model, field);
            if (fieldValue != null) {
                newFieldValuesMap.put(field,fieldValue);
            }
        }
        try {
            newFieldValuesMap = MappingHandler.mappingHandle(modelName,newFieldValuesMap);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return executePreparedStatementInsert(tableName, newFieldValuesMap);
    }

    @Override
    public T getModel(String sql) {
        T resultModel = null;
        try {
            ResultSet resultSet = executeStatementQuery(sql);
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultModel = modelResultFactory.getResultModel(resultSet, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultModel;
    }

    @Override
    public T getModel(String sql, ArrayList<String> conditionValues) {
        T resultModel = null;
        try {
            ResultSet resultSet = executePreparedStatementQuery(sql,conditionValues);
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultModel = modelResultFactory.getResultModel(resultSet, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultModel;
    }

    @Override
    public T getModel(HashMap<String, Object> conditionsMap, ArrayList<String> queryFieldList,String separateMark) {
        T resultModel = null;
        try {
            queryFieldList = MappingHandler.mappingHandle(modelName,queryFieldList);
            conditionsMap = MappingHandler.mappingHandle(modelName,conditionsMap);
            ResultSet resultSet = executePreparedStatementQuery(tableName, queryFieldList, conditionsMap,separateMark);
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultModel = modelResultFactory.getResultModel(resultSet, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultModel;
    }

    @Override
    public Object getModelFieldValue(String fieldName, HashMap<String, Object> conditionsMap,String separateMark) {
        Object value = null;
        try {
            conditionsMap = MappingHandler.mappingHandle(modelName,conditionsMap);
            fieldName = MappingHandler.mappingHandle(modelName,fieldName);
            ResultSet resultSet = executePreparedStatementQuery(tableName, YouCollectionsUtils.getYouArrayList(fieldName), conditionsMap,separateMark);
            if (!resultSet.isBeforeFirst()) {
                return null;
            } else {
                resultSet.next();
                value = resultSet.getObject(fieldName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public int updateModel(HashMap<String, Object> newFieldValuesMap, HashMap<String, Object> conditionsMap,String separateMark) throws NoneffectiveUpdateExecuteException {
        try {
            newFieldValuesMap = MappingHandler.mappingHandle(modelName,newFieldValuesMap);
            conditionsMap = MappingHandler.mappingHandle(modelName,conditionsMap);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return executePreparedStatementUpdate(tableName,newFieldValuesMap,conditionsMap,separateMark);
    }

    @Override
    public int deleteModel(HashMap<String, Object> conditionsMap,String separateMark) throws NoneffectiveUpdateExecuteException {
        try {
            conditionsMap = MappingHandler.mappingHandle(modelName,conditionsMap);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return executePreparedStatementDelete(tableName,conditionsMap,separateMark);
    }

    @Override
    public int addition(String modelField, double b, HashMap<String, Object> conditionsMap) throws NoneffectiveUpdateExecuteException {
        return basicArithmetic(modelField,b,conditionsMap,"+");
    }

    @Override
    public int subtraction(String modelField, double b, HashMap<String, Object> conditionsMap) throws NoneffectiveUpdateExecuteException {
        return basicArithmetic(modelField,b,conditionsMap, "-");
    }

    @Override
    public int multiplication(String modelField, double b, HashMap<String, Object> conditionsMap) throws NoneffectiveUpdateExecuteException {
        return basicArithmetic(modelField,b,conditionsMap, "*");
    }

    @Override
    public int division(String modelField, double b, HashMap<String, Object> conditionsMap) throws NoneffectiveUpdateExecuteException {
        return basicArithmetic(modelField,b,conditionsMap, "/");
    }

    private int basicArithmetic(String modelField, double b, HashMap<String, Object> conditionsMap, String op) throws NoneffectiveUpdateExecuteException {
        String tableField = null;
        try {
            tableField = MappingHandler.mappingHandle(modelName, modelField);
            conditionsMap = MappingHandler.mappingHandle(modelName,conditionsMap);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        ArrayList<Object> conditionValues = new ArrayList<>(conditionsMap.values());
        String sql = "UPDATE " +
                tableName +
                " SET " +
                tableField +
                " = " +
                tableField +
                op +
                " ?" +
                SqlStringUtils.getWhereSubStr(conditionsMap.keySet(), "AND");
        return executePreparedStatementUpdate(sql, YouCollectionsUtils.getYouArrayList(b+""), conditionValues);
    }
}
