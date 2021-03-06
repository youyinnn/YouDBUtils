package com.github.youyinnn.youdbutils.dao.model;


import com.github.youyinnn.youdbutils.exceptions.ModelResultTransferException;
import com.github.youyinnn.youwebutils.third.ReflectionUtils;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * 处理ResultSet为Model类组成的List.
 *
 * @author youyinnn
 */
public class ModelResultFactory<T> {

    private Class<T> modelClass;

    private ArrayList<String> fieldList;

    private FieldMapping fieldMapping;

    public ModelResultFactory(Class<T> modelClass) {
        this.modelClass = modelClass;
        this.fieldList = ModelTableMessage.getModelFieldList(modelClass.getSimpleName());
        this.fieldMapping = ModelTableMessage.getFieldMapping(modelClass.getSimpleName());
    }

    public ArrayList<T> getResultModelList(ResultSet resultSet) {
        ArrayList<T> resultModelList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                T instance = getResultModel(resultSet, false);
                resultModelList.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultModelList;
    }


    public T getResultModel(ResultSet resultSet, boolean isOneResult) {
        T instance = null;
        try {
            if (isOneResult) {
                resultSet.next();
            }
            if (fieldList == null | fieldMapping == null) {
                throw new ModelResultTransferException("Model：["+modelClass.getSimpleName()+"] " +
                        "没有在ModelTableMessage中注册。");
            }
            instance = modelClass.newInstance();
            for (String field : fieldList) {
                boolean rsHasThisField = true;
                try {
                    resultSet.findColumn(fieldMapping.getTableField(field));
                } catch (Exception ignore) {
                    rsHasThisField = false;
                }
                if (rsHasThisField) {
                    Object value;
                    if (fieldMapping.needToReplace(field)) {
                        value = resultSet.getObject(fieldMapping.getTableField(field));
                    } else {
                        value = resultSet.getObject(field);
                    }
                    ReflectionUtils.setFieldValue(instance,field,value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instance;
    }

    @Override
    public String toString() {
        return "ModelResultFactory{" +
                "modelClass=" + modelClass +
                ", fieldList=" + fieldList +
                ", fieldMapping=" + fieldMapping +
                '}';
    }
}
