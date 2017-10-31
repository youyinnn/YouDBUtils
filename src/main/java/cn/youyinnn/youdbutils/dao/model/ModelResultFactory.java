package cn.youyinnn.youdbutils.dao.model;


import cn.youyinnn.youdbutils.utils.ReflectionUtils;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/10/3
 */
public class ModelResultFactory<T> {

    private Class<T> modelClass;

    private ArrayList<String> fieldList;

    private FieldMap fieldMap;

    public ModelResultFactory(Class<T> modelClass) {
        this.modelClass = modelClass;
        this.fieldList = ModelTableMessage.getModelFieldList(modelClass.getSimpleName());
        this.fieldMap = ModelTableMessage.getFieldMap(modelClass.getSimpleName());
    }

    public ArrayList<T> getResultModelList(ResultSet resultSet) {
        ArrayList<T> resultModelList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                T instance = getResultModel(resultSet);
                resultModelList.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultModelList;
    }


    public T getResultModel(ResultSet resultSet){

        T instance = null;
        try {
            instance = modelClass.newInstance();
            for (String field : fieldList) {
                Object value;
                if (fieldMap.needToReplace(field)) {
                    value = resultSet.getObject(fieldMap.getTableField(field));
                } else {
                    value = resultSet.getObject(field);
                }
                ReflectionUtils.setFieldValue(instance,field,value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instance;
    }

}
