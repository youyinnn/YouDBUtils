package cn.youyinnn.youdbutils.dao.model;


import cn.youyinnn.youdbutils.exceptions.ModelResultTransferException;
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


    public T getResultModel(ResultSet resultSet) {

        T instance = null;
        try {
            if (fieldList == null || fieldMap == null) {
                throw new ModelResultTransferException("Model：["+modelClass.getSimpleName()+"] " +
                        "没有在ModelTableMessage中注册，无法使用ModelHandler去处理结果集，如果在单独使用YouDao的时候，" +
                        "需要同时支持ModelHandler的服务，请在实例化YouDao对象之前使用YouDbManager.scanPackageForModel(packagePath)" +
                        "方法注册所有的Model信息。");
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
                    if (fieldMap.needToReplace(field)) {
                        value = resultSet.getObject(fieldMap.getTableField(field));
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
