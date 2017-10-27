package cn.youyinnn.youdbutils.dao.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * @description:
 * @author: youyinnn
 * @date: 2017/10/1
 */
public class ModelTableMessage {

    private static HashMap<String,ArrayList<String>> modelField = new HashMap<>();

    private static HashMap<String,ArrayList<String>> tableField = new HashMap<>();

    private static HashMap<String,FieldMap> fieldMapping = new HashMap<>();

    public static void addModelField(String modelClassName, ArrayList<String> fieldList) {
        modelField.put(modelClassName,fieldList);
    }

    public static void addTableField(String tableName, ArrayList<String> fieldList) {
        tableField.put(tableName,fieldList);
    }

    public static FieldMap getFieldMap(String modelName) {
        return fieldMapping.get(modelName);
    }

    public static void setFieldMapping() {
        for (String modelName : modelField.keySet()) {
            fieldMapping.put(modelName,
                    new FieldMap(modelName,modelField.get(modelName),tableField.get(modelName)));
        }
    }

    public static HashMap<String, ArrayList<String>> getModelField() {
        return modelField;
    }

    public static HashMap<String, ArrayList<String>> getTableField() {
        return tableField;
    }

    public static ArrayList<String> getModelFieldList(String modelClassName) {
        return modelField.get(modelClassName);
    }

    public static Set<String> getModelFieldSet() {
        return modelField.keySet();
    }

    public static ArrayList<String> getTableFieldList(String modelClassName) {
        return tableField.get(modelClassName);
    }

    public static HashMap<String, FieldMap> getFieldMapping() {
        return fieldMapping;
    }
}
