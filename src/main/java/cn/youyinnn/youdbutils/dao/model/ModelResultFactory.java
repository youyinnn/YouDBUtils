package cn.youyinnn.youdbutils.dao.model;


import cn.youyinnn.youdbutils.exceptions.ModelResultTransferException;
import cn.youyinnn.youdbutils.utils.ReflectionUtils;

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
                T instance = getResultModel(resultSet);
                resultModelList.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultModelList;
    }


    public T getResultModel(ResultSet resultSet) {

        T instance = null;
        try {
            if (fieldList == null | fieldMapping == null) {
                throw new ModelResultTransferException("Model：["+modelClass.getSimpleName()+"] " +
                        "没有在ModelTableMessage中注册。");
            }
            instance = modelClass.newInstance();
            for (String field : fieldList) {
                boolean rsHasThisField = true;
                try {
                    resultSet.findColumn(field);
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

}
