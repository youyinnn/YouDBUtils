package cn.youyinnn.youdbutils.dao.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * 处理各种modelField集的映射,返回映射后的集.
 *
 * @author youyinnn
 */
public class MappingHandler {

    /**
     * 把传入的model字段列表映射成table字段列表
     *
     * @param modelName  the model name
     * @param modelField the model field
     * @return the array list
     */
    public static ArrayList<String> mappingHandle(String modelName, ArrayList<String> modelField) throws NoSuchFieldException {

        if (modelField == null) {
            return null;
        }

        FieldMapping fieldMapping = ModelTableMessage.getFieldMapping(modelName);

        if (fieldMapping != null) {
            ArrayList<String> result = new ArrayList<>();

            for (String mField : modelField) {
                if (fieldMapping.needToReplace(mField)) {
                    result.add(fieldMapping.getTableField(mField));
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
    public static ArrayList<String> mappingHandle(String modelName, Set<String> modelField) throws NoSuchFieldException {
        return mappingHandle(modelName,new ArrayList<>(modelField));
    }

    /**
     * 把传入的model字段Map中的key值映射成table字段Map
     *
     * @param modelName  the model name
     * @param modelField the model field
     * @return the hash map
     */
    public static HashMap<String, Object> mappingHandle(String modelName, HashMap<String, Object> modelField) throws NoSuchFieldException {

        if (modelField == null) {
            return null;
        }

        HashMap<String, Object> tableField = new HashMap<>(10);

        FieldMapping fieldMapping = ModelTableMessage.getFieldMapping(modelName);

        Set<String> mFieldSet = modelField.keySet();

        for (String mField : mFieldSet) {
            Object value = modelField.get(mField);
            if (fieldMapping.needToReplace(mField)) {
                tableField.put(fieldMapping.getTableField(mField), value);
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
    public static String mappingHandle(String modelName, String modelField) throws NoSuchFieldException {

        FieldMapping fieldMapping = ModelTableMessage.getFieldMapping(modelName);

        if (fieldMapping.needToReplace(modelField)) {
            return fieldMapping.getTableField(modelField);
        }

        return modelField;
    }

}
