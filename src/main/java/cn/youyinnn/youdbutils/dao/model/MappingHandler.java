package cn.youyinnn.youdbutils.dao.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * The type Mapping handler.
 *
 * @description:
 * @author: youyinnn
 * @date: 2017 /10/26
 */
public class MappingHandler {

    /**
     * 把传入的model字段列表映射成table字段列表
     *
     * @param modelName  the model name
     * @param modelField the model field
     * @return the array list
     */
    public static ArrayList<String> mappingHandle(String modelName, ArrayList<String> modelField) {

        if (modelField == null) {
            return null;
        }

        FieldMap fieldMap = ModelTableMessage.getFieldMap(modelName);

        if (fieldMap != null) {
            ArrayList<String> result = new ArrayList<>();

            for (String mField : modelField) {
                if (fieldMap.needToReplace(mField)) {
                    result.add(fieldMap.getTableField(mField));
                } else {
                    result.add(mField);
                }
            }

            return result;
        } else {
            return modelField;
        }
    }

    /**
     * 把传入的model字段集合映射成table字段集合
     *
     * @param modelName  the model name
     * @param modelField the model field
     * @return the array list
     */
    public static ArrayList<String> mappingHandle(String modelName, Set<String> modelField) {
        return mappingHandle(modelName,new ArrayList<>(modelField));
    }

    /**
     * 把传入的model字段Map中的key值映射成table字段Map
     *
     * @param modelName  the model name
     * @param modelField the model field
     * @return the hash map
     */
    public static HashMap<String, Object> mappingHandle(String modelName, HashMap<String, Object> modelField){

        if (modelField == null) {
            return null;
        }

        HashMap<String, Object> tableField = new HashMap<>();

        FieldMap fieldMap = ModelTableMessage.getFieldMap(modelName);

        Set<String> mFieldSet = modelField.keySet();

        for (String mField : mFieldSet) {
            Object value = modelField.get(mField);
            if (fieldMap.needToReplace(mField)) {
                tableField.put(fieldMap.getTableField(mField), value);
            } else {
                tableField.put(mField,value);
            }

        }

        return tableField;
    }

    /**
     * 把传入的model字段映射成table字段
     *
     * @param modelName  the model name
     * @param modelField the model field
     * @return the string
     */
    public static String mappingHandle(String modelName, String modelField) {

        FieldMap fieldMap = ModelTableMessage.getFieldMap(modelName);

        return fieldMap.getTableField(modelField);

    }

}
